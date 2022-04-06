package com.swissre.coffee_corner.offerings;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Offerings {
    private final static Offerings myOfferings = new Offerings();
    private final Map<String, Product> productsByName;
    private final Map<Integer, Product> productsById;

    public static Offerings getOfferings() {
        return myOfferings;
    }

    /**
     * The products are created including their category and prices. In future releases this definition can be converted
     * into a config file for flexibility, but at the moment is done hard coded for simplicity...
     */
    private Offerings() {
        this.productsByName = new HashMap<>();
        this.productsById = new HashMap<>();

        Product newProduct = new Product(1, "small coffee", Product.Category.beverage, new BigDecimal("2.50"));
        this.productsByName.put(newProduct.getName(), newProduct);
        this.productsById.put(newProduct.getId(), newProduct);

        newProduct = new Product(2,"medium coffee", Product.Category.beverage, new BigDecimal("3.00"));
        this.productsByName.put(newProduct.getName(), newProduct);
        this.productsById.put(newProduct.getId(), newProduct);

        newProduct = new Product(3,"large coffee", Product.Category.beverage, new BigDecimal("3.50"));
        this.productsByName.put(newProduct.getName(), newProduct);
        this.productsById.put(newProduct.getId(), newProduct);

        newProduct = new Product(4,"orange juice", Product.Category.beverage, new BigDecimal("3.95"));
        this.productsByName.put(newProduct.getName(), newProduct);
        this.productsById.put(newProduct.getId(), newProduct);

        newProduct = new Product(5,"bacon roll", Product.Category.snack, new BigDecimal("4.50"));
        this.productsByName.put(newProduct.getName(), newProduct);
        this.productsById.put(newProduct.getId(), newProduct);

        newProduct = new Product(6,"extra milk", Product.Category.extra, new BigDecimal("0.30"));
        this.productsByName.put(newProduct.getName(), newProduct);
        this.productsById.put(newProduct.getId(), newProduct);

        newProduct = new Product(7,"foamed milk", Product.Category.extra, new BigDecimal("0.50"));
        this.productsByName.put(newProduct.getName(), newProduct);
        this.productsById.put(newProduct.getId(), newProduct);

        newProduct = new Product(8,"special roast", Product.Category.extra, new BigDecimal("0.90"));
        this.productsByName.put(newProduct.getName(), newProduct);
        this.productsById.put(newProduct.getId(), newProduct);
    }

    public Product getProductByName(String productName) throws UnknownProductException {
        if (productsByName.containsKey(productName)) {
            return productsByName.get(productName);

        } else {
            throw new UnknownProductException("Product not found: " + productName);
        }
    }

    public Product getProductById(Integer id) {
        return productsById.get(id);
    }



    public Set<Integer> getIdsByCategory(Product.Category category) {
        return productsById.entrySet().stream()
                .filter(p -> p.getValue().getCategory() == category)
                .collect(Collectors.toMap(p->p.getKey(), p->p.getValue()))
                .keySet();
    }
}
