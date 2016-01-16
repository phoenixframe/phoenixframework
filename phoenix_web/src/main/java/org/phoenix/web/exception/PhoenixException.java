package org.phoenix.web.exception;

@SuppressWarnings("serial")
public class PhoenixException extends RuntimeException {

	public PhoenixException() {
		super();
	}

	public PhoenixException(String message, Throwable cause) {
		super(message, cause);
	}

	public PhoenixException(String message) {
		super(message);
	}

	public PhoenixException(Throwable cause) {
		super(cause);
	}
	
}
