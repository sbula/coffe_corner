package com.swissre.coffee_corner;

import com.swissre.coffee_corner.offerings.UnknownProductException;

public class CoffeeCorner {

    /**
     *
     * @param args String representing the order, Number of stamps on the stampCard
     */
    public static void main(String[] args)  {
        Order myOrder = new Order();

        String orderString = args[0];
        Integer stampCount = Integer.valueOf(args[1]);

        try {
            myOrder.createOrderMap(orderString);

        } catch (UnknownProductException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(myOrder.writeRecipt(stampCount));
    }
}
