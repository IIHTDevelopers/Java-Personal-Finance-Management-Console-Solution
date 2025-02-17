package com.finance.exception;

public class BudgetExceededException extends Exception {

	public BudgetExceededException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "Budget Exceeded Exception: " + getMessage();
	}
}
