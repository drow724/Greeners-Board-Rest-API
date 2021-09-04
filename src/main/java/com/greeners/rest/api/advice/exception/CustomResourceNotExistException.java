package com.greeners.rest.api.advice.exception;

public class CustomResourceNotExistException extends RuntimeException {
    public CustomResourceNotExistException(String message, Throwable t) {
        super(message, t);
    }

    public CustomResourceNotExistException(String message) {
        super(message);
    }

    public CustomResourceNotExistException() {
        super();
    }
}