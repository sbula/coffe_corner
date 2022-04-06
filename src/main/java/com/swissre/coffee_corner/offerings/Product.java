package com.swissre.coffee_corner.offerings;

import java.math.BigDecimal;

/**
 * The product contains the basic information of the product itself - each of the products are unique to the system.
 * To keep it simple, the products will be hardcoded at the startup for the moment - keep in mind for a future release
 * they could be initialised using a configuration file...
 */
public class Product {

    public enum Category {beverage, snack, extra};

    private int id;
    private String name;
    private Category category;
    private BigDecimal price;

    public Product(int id, String name, Category category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
