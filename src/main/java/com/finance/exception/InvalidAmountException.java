package com.finance.exception;

public class InvalidAmountException extends Exception {

	public InvalidAmountException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "Invalid Amount Exception: " + getMessage();
	}
}
