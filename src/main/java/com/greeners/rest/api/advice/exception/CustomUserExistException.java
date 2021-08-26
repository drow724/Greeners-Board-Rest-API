package com.greeners.rest.api.advice.exception;

public class CustomUserExistException extends RuntimeException {
    public CustomUserExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public CustomUserExistException(String msg) {
        super(msg);
    }

    public CustomUserExistException() {
        super();
    }
}