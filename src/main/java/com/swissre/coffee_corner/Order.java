package com.swissre.coffee_corner;

import com.swissre.coffee_corner.offerings.BonusProgramms;
import com.swissre.coffee_corner.offerings.Offerings;
import com.swissre.coffee_corner.offerings.Product;
import com.swissre.coffee_corner.offerings.UnknownProductException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Order {
    private static final String __HEADER__ = "Cha@rlene's Coffee Corner\n\n";

    private Offerings offerings;
    private Map<Integer, Integer> orderMap;

    public Order() {
        this.offerings = Offerings.getOfferings();
    }

    /**
     * read the formated string to order beverages, snacks and extras
     * @param order
     * @return
     */
    public List<String> readOrder(String order) {
        List<String> orderList = Stream.of(order.toLowerCase(Locale.ROOT)
                        .split(",|with"))
                .map(String::trim)
                .collect(Collectors.toList());

        return orderList;
    }

    /**
     * read the formated string to order beverages, snacks and extras - check the order items and count them
     * @param order
     * @return
     * @throws UnknownProductException
     */

    public void createOrderMap(String order) throws UnknownProductException {
        orderMap = new HashMap<>();

        for (String o : readOrder(order)) {
            Product p = offerings.getProductByName(o); // just check if the order is available in the coffee shop...
            if (!orderMap.containsKey(p.getId())) {
                orderMap.put(p.getId(), 1);
            } else {
                orderMap.put(p.getId(), orderMap.get(p.getId()) + 1);
            }
        }
    }

    public String writeRecipt (Integer stampCount)  {
        BigDecimal sum = new BigDecimal(BigInteger.ZERO,  2);

        // write a header
        StringBuilder reciptBuilder = new StringBuilder(__HEADER__);

        // list the order and sum up the positions
        for (Integer id : orderMap.keySet()) {
            String name = String.format("%-15s", offerings.getProductById(id).getName());
            Integer quantity = orderMap.get(id);
            BigDecimal price = offerings.getProductById(id).getPrice();
            BigDecimal posTotal = price.multiply(new BigDecimal(quantity));
            sum = sum.add(posTotal);

            if (quantity == 1) {
                reciptBuilder.append(name).append(" --> ").append(String.format("%7s", posTotal)).append("\n");

            } else {
                reciptBuilder.append(name).append("\n")
                        .append(String.format("%-15s", "   "+ quantity + " * " + price))
                        .append(" --> ").append(String.format("%7s", posTotal)).append("\n");
            }
        }

        // check bonus programs
        BigDecimal stampBonus = BonusProgramms.checkStamp(orderMap, stampCount);
        if (stampBonus.compareTo(BigDecimal.ZERO) < 0) {
            reciptBuilder.append(String.format("%-15s", "stamp bonus")).append(" --> ")
                    .append(String.format("%7s", stampBonus)).append("\n");
            sum = sum.add(stampBonus);
        }

        BigDecimal bevAndSnackBonus = BonusProgramms.checkBeverageAndSnack(orderMap);
        if (bevAndSnackBonus.compareTo(BigDecimal.ZERO) < 0) {
            reciptBuilder.append(String.format("%-15s", "drink & snack")).append(" --> ")
                    .append(String.format("%7s", bevAndSnackBonus)).append("\n");
            sum = sum.add(bevAndSnackBonus);
        }

        // sum it up..
        reciptBuilder.append(String.format("%-15s", "total"))
                .append(" ==> ").append(String.format("%7s", sum));

        return reciptBuilder.toString();
    }


    public Map<Integer, Integer> getOrderMap() {
        return this.orderMap;
    }
}
