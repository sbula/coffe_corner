package com.swissre.coffee_corner;

import com.swissre.coffee_corner.offerings.Offerings;
import com.swissre.coffee_corner.offerings.Product;
import com.swissre.coffee_corner.offerings.UnknownProductException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private static Order myOrder;
    private static Offerings myOfferings;

    @BeforeAll
    static void initAll() {
        myOrder = new Order();
        myOfferings = Offerings.getOfferings();
    }

    @Test
    public void readSimpleOrder() {
        List<String> orderList = myOrder.readOrder("Small coffee");

        assertEquals("small coffee", orderList.get(0));
    }

    @Test
    public void readLargerOrder() {
        List<String> orderList = myOrder.readOrder("Large Coffee, roast Bacon Roll, orange juice");

        assertEquals("large coffee", orderList.get(0));
        assertEquals("roast bacon roll", orderList.get(1));
        assertEquals("orange juice", orderList.get(2));
    }

    @Test
    public void readExtraOrder() {
        List<String> orderList = myOrder.readOrder("Large Coffee with special roast, orange juice");

        assertEquals("large coffee", orderList.get(0));
        assertEquals("special roast", orderList.get(1));
        assertEquals("orange juice", orderList.get(2));
    }

    @Test
    public void mapSimpleOrder() {
        try {
            myOrder.createOrderMap("Large Coffee with special roast, orange juice");
            Map<Integer, Integer> orderMap = myOrder.getOrderMap();

            assertEquals(1, orderMap.get(myOfferings.getProductByName("large coffee").getId()));
            assertEquals(1, orderMap.get(myOfferings.getProductByName("special roast").getId()));
            assertEquals(1, orderMap.get(myOfferings.getProductByName("orange juice").getId()));

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void mapMultipleOrder() {
        try {
            myOrder.createOrderMap("Large Coffee with special roast, orange juice, large Coffee with Foamed milk");
            Map<Integer, Integer> orderMap = myOrder.getOrderMap();

            assertEquals(2, orderMap.get(myOfferings.getProductByName("large coffee").getId()));
            assertEquals(1, orderMap.get(myOfferings.getProductByName("special roast").getId()));
            assertEquals(1, orderMap.get(myOfferings.getProductByName("foamed milk").getId()));
            assertEquals(1, orderMap.get(myOfferings.getProductByName("orange juice").getId()));

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void mapUnknownOrder() {
        assertThrows(UnknownProductException.class, () ->
                myOrder.createOrderMap("Large Coffee with special roast, orange juice, large Coffee with special sugar")
        );
    }

    @Test
    public void simpleReciptTest() {
        try {
            myOrder.createOrderMap("Large Coffee with special roast");
            String myRecipt = myOrder.writeRecipt(0);

            assertEquals("Cha@rlene's Coffee Corner\n" +
                    "\n" +
                    "large coffee    -->    3.50\n" +
                    "special roast   -->    0.90\n" +
                    "total           ==>    4.40", myRecipt);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void twoCoffeeReciptTest() {
        try {
            myOrder.createOrderMap("Large Coffee with special roast, Large Coffee");
            String myRecipt = myOrder.writeRecipt(0);

            assertEquals("Cha@rlene's Coffee Corner\n" +
                    "\n" +
                    "large coffee   \n" +
                    "   2 * 3.50     -->    7.00\n" +
                    "special roast   -->    0.90\n" +
                    "total           ==>    7.90", myRecipt);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void twoCoffee4StampsReciptTest() {
        try {
            myOrder.createOrderMap("Large Coffee with special roast, Large Coffee");
            String myRecipt = myOrder.writeRecipt(4);

            assertEquals("Cha@rlene's Coffee Corner\n" +
                    "\n" +
                    "large coffee   \n" +
                    "   2 * 3.50     -->    7.00\n" +
                    "special roast   -->    0.90\n" +
                    "stamp bonus     -->   -3.50\n" +
                    "total           ==>    4.40", myRecipt);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void twoCoffee9StampsReciptTest() {
        try {
            myOrder.createOrderMap("Large Coffee with special roast, Large Coffee");
            String myRecipt = myOrder.writeRecipt(9);

            assertEquals("Cha@rlene's Coffee Corner\n" +
                    "\n" +
                    "large coffee   \n" +
                    "   2 * 3.50     -->    7.00\n" +
                    "special roast   -->    0.90\n" +
                    "stamp bonus     -->   -7.00\n" +
                    "total           ==>    0.90", myRecipt);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void twoCoffee88StampsReciptTest() {
        try {
            myOrder.createOrderMap("Large Coffee with special roast, Large Coffee");
            String myRecipt = myOrder.writeRecipt(88);

            assertEquals("Cha@rlene's Coffee Corner\n" +
                    "\n" +
                    "large coffee   \n" +
                    "   2 * 3.50     -->    7.00\n" +
                    "special roast   -->    0.90\n" +
                    "stamp bonus     -->   -7.00\n" +
                    "total           ==>    0.90", myRecipt);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void twoCoffeeAndSnackReciptTest() {
        try {
            myOrder.createOrderMap("Large Coffee with special roast, Large Coffee, bacon roll");
            String myRecipt = myOrder.writeRecipt(2);

            assertEquals("Cha@rlene's Coffee Corner\n" +
                    "\n" +
                    "large coffee   \n" +
                    "   2 * 3.50     -->    7.00\n" +
                    "bacon roll      -->    4.50\n" +
                    "special roast   -->    0.90\n" +
                    "drink & snack   -->   -0.90\n" +
                    "total           ==>   11.50", myRecipt);
        } catch (Exception e) {
            fail(e);
        }
    }


}
