package com.github.javlock.system.systemd;

public class NotParsedException extends Exception {

	private static final long serialVersionUID = 6701125982724514310L;

	public NotParsedException() {
		super();
	}

	public NotParsedException(String message) {
		super(message);
	}

	public NotParsedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotParsedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotParsedException(Throwable cause) {
		super(cause);
	}

}
