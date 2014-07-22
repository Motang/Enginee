package com.engineer.webservice;

public class HelloException extends Exception {
	private String message;

	public HelloException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
