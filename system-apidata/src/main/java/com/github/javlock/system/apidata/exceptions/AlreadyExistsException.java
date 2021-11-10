package com.github.javlock.system.apidata.exceptions;

public class AlreadyExistsException extends Exception {

	private static final long serialVersionUID = 824228634999451810L;

	public AlreadyExistsException() {
	}

	public AlreadyExistsException(String message) {
		super(message);
	}

	public AlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
