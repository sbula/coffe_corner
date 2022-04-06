package com.swissre.coffee_corner.offerings;

import com.swissre.coffee_corner.CoffeeCorner;
import com.swissre.coffee_corner.offerings.Product;
import com.swissre.coffee_corner.offerings.UnknownProductException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class OfferingsTest {

    static private Offerings myOfferings;

    @BeforeAll
    static void initAll() {
        myOfferings = Offerings.getOfferings();
    }

    @AfterAll
    static void tearDownAll() {}

    @Test
    void unknownProduct() {
        assertThrows(UnknownProductException.class, () ->
                myOfferings.getProductByName("Big Black Tea")
        );
    }

    @Test
    void getSmallCoffee() {
        Product p1;
        try {
            p1 = myOfferings.getProductByName("small coffee");
            assertEquals("small coffee".toLowerCase(Locale.ROOT), p1.getName());
            assertEquals(Product.Category.beverage, p1.getCategory());
            assertEquals(new BigDecimal("2.50"), p1.getPrice());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void getMediumCoffeeByID() {
        Product p1;
        try {
            p1 = myOfferings.getProductById(2);
            assertEquals("medium coffee".toLowerCase(Locale.ROOT), p1.getName());
            assertEquals(Product.Category.beverage, p1.getCategory());
            assertEquals(new BigDecimal("3.00"), p1.getPrice());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void getBaconRoll() {
        Product p1;
        try {
            p1 = myOfferings.getProductByName("bacon roll");
            assertEquals("bacon roll", p1.getName());
            assertEquals(Product.Category.snack, p1.getCategory());
            assertEquals(new BigDecimal("4.50"), p1.getPrice());

        } catch (Exception e) {
            fail(e);
        }
    }
}
