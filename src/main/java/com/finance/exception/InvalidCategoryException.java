package com.finance.exception;

public class InvalidCategoryException extends Exception {

	public InvalidCategoryException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "Invalid Category Exception: " + getMessage();
	}
}
