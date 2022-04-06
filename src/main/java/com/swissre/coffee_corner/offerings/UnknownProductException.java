package com.swissre.coffee_corner.offerings;

public class UnknownProductException extends Exception {
    public UnknownProductException (String errorMsg) {
        super (errorMsg);
    }
}
