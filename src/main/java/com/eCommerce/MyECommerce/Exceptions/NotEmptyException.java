package com.eCommerce.MyECommerce.Exceptions;

public class NotEmptyException extends RuntimeException {

    private static final String ERROR_CODE = "E-Commerce.Can't-be-empty";

    public NotEmptyException (String message) {
        super(String.format("%s: %s", ERROR_CODE, message));
    }
}