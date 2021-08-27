package com.greeners.rest.api.advice.exception;

public class CustomForbiddenWordException extends RuntimeException {

    public CustomForbiddenWordException(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomForbiddenWordException(String msg) {
        super(msg);
    }

    public CustomForbiddenWordException() {
        super();
    }
}