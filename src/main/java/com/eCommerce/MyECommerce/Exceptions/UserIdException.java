package com.eCommerce.MyECommerce.Exceptions;

public class UserIdException extends RuntimeException {

    private static final String ERROR_CODE = "E-Commerce.Invalid-User";

    public UserIdException(String message) {
        super(String.format("%s: %s", ERROR_CODE, message));
    }
}
