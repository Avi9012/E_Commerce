package com.eCommerce.MyECommerce.Exceptions;

public class InvalidOrderAmountException extends RuntimeException {

    private static final String ERROR_CODE = "E-Commerce.invalid-order-amount";

    public InvalidOrderAmountException(String message) {
        super(String.format("%s; %s", ERROR_CODE, message));
    }
}
