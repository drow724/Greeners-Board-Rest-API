package com.greeners.rest.api.advice.exception;

public class CustomNotOwnerException extends RuntimeException {

	private static final long serialVersionUID = 2241549550934267615L;

	public CustomNotOwnerException(String message, Throwable t) {
		super(message, t);
	}

	public CustomNotOwnerException(String message) {
		super(message);
	}

	public CustomNotOwnerException() {
		super();
	}
}