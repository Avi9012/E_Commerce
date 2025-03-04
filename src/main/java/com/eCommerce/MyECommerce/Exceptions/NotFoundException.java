package com.eCommerce.MyECommerce.Exceptions;

public class NotFoundException extends RuntimeException {

    private static final String ERROR_CODE = "E-Commerce.not-found";

    public NotFoundException(String message) {
        super(String.format("%s: %s", ERROR_CODE, message));
    }
}
