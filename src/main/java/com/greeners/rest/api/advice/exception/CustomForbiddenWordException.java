package com.greeners.rest.api.advice.exception;

public class CustomForbiddenWordException extends RuntimeException {

    public CustomForbiddenWordException(String message, Throwable t) {
        super(message, t);
    }

    public CustomForbiddenWordException(String message) {
        super(message);
    }

    public CustomForbiddenWordException() {
        super();
    }
}