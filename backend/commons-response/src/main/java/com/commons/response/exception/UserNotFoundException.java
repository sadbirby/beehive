package com.commons.response.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("No user with ID: " + id);
    }
}
