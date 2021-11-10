package com.github.javlock.system.apidata.exceptions;

public class ObjectTypeException extends Exception {
	private static final long serialVersionUID = -5324960707341542843L;

	public ObjectTypeException() {
	}

	public ObjectTypeException(String message) {
		super(message);
	}

	public ObjectTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ObjectTypeException(Throwable cause) {
		super(cause);
	}

}
