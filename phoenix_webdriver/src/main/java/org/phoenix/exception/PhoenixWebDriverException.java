package org.phoenix.exception;

public class PhoenixWebDriverException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhoenixWebDriverException() {
		super("操作发生异常");
	}

	public PhoenixWebDriverException(String message) {
		super(message);
	}

	public PhoenixWebDriverException(Throwable cause) {
		super(cause);
	}

	public PhoenixWebDriverException(String message, Throwable cause) {
		super(message, cause);
	}
}
