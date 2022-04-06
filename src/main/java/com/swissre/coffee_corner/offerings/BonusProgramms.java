package com.swissre.coffee_corner.offerings;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class BonusProgramms {
    private static final Offerings offerings = Offerings.getOfferings();


    private static Map<Integer, Integer> filterByCategory(Map<Integer, Integer> orderMap, Product.Category category) {
        Set<Integer> myCatIds = offerings.getIdsByCategory(category);
        Map<Integer, Integer> myCatMap = orderMap.entrySet().stream()
                .filter(p -> myCatIds.contains(p.getKey()))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        return myCatMap;
    }

    /**
     * A customer stamp card where every 5th beverage is for free
     * @param orderMap
     * @param stampCount
     * @return
     */
    public static BigDecimal checkStamp(Map<Integer, Integer> orderMap, int stampCount) {
        // count the newly ordered beverages and add them to the stampCount
        Map<Integer, Integer> myBeverages = filterByCategory(orderMap, Product.Category.beverage);
        Integer sum = stampCount + myBeverages.values().stream()
                .reduce(0, Integer::sum);

        // you are entitled for xxx free beverages, for now, we just start with the one with the lowes id...
        List<Integer> idList = myBeverages.keySet().stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        Iterator<Integer> iter = idList.listIterator();
        int forFree = sum / 5;
        return computeDiscount(myBeverages, forFree, iter);
    }

    /**
     * for a beverage and a snack in the order, there is a free extra
     * @param orderMap
     * @return
     */
    public static BigDecimal checkBeverageAndSnack(Map<Integer, Integer> orderMap) {
        //count my beverages...
        Map<Integer, Integer> myBeverages = filterByCategory(orderMap, Product.Category.beverage);
        Integer beverageCount = myBeverages.values().stream()
                .reduce(0, Integer::sum);

        // count my snacks
        Map<Integer, Integer> mySnacks = filterByCategory(orderMap, Product.Category.snack);
        Integer snackCount = mySnacks.values().stream()
                .reduce(0, Integer::sum);

        // you are entitled to get xxx free extras...
        int freeExtras = 0;
        if (beverageCount >= snackCount) {
            freeExtras = snackCount;
        } else {
            freeExtras = beverageCount;
        }

        Map<Integer, Integer> myExtras = filterByCategory(orderMap, Product.Category.extra);
        List<Integer> idList = myExtras.keySet().stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        Iterator<Integer> iter = idList.listIterator();
        return computeDiscount(myExtras, freeExtras, iter);
    }

    /**
     * Compute the amount to be deducted
     * @param myCategoryItems Map of the ordered item in the dedicated category
     * @param freeItems number of items to be for free
     * @param iter iterator over the product ids in the dedicated category
     * @return
     */
    private static BigDecimal computeDiscount(Map<Integer, Integer> myCategoryItems, int freeItems, Iterator<Integer> iter) {
        BigDecimal myDiscount = new BigDecimal("0.00");
        while (freeItems >= 1 && iter.hasNext()) {
            Integer id = iter.next();
            Integer count = myCategoryItems.get(id);
            if (freeItems <= count) {
                BigDecimal price = offerings.getProductById(id).getPrice();
                BigDecimal reduction = price.multiply(BigDecimal.valueOf(freeItems));
                myDiscount = myDiscount.add(reduction);
                freeItems = 0;
            } else {
                myDiscount = myDiscount.add(offerings.getProductById(id).getPrice().multiply(BigDecimal.valueOf(count)));
                freeItems -= count;
            }
        }
        return myDiscount.negate();
    }
}
