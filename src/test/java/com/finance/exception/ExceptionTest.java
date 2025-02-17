package com.finance.exception;

import static com.finance.testutils.TestUtils.currentTest;
import static com.finance.testutils.TestUtils.exceptionTestFile;
import static com.finance.testutils.TestUtils.testReport;
import static com.finance.testutils.TestUtils.yakshaAssert;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.finance.inventory.TransactionManager;
import com.finance.models.Transaction;

class TransactionManagerExceptionTest {

	private TransactionManager transactionManager;

	@BeforeEach
	public void setUp() {
		transactionManager = new TransactionManager();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	// Test for InvalidAmountException when adding an expense with a non-positive
	// amount
	@Test
	public void testAddExpenseWithInvalidAmount() throws IOException, InvalidCategoryException {
		try {
			transactionManager.addExpense(-500.0, "Invalid Amount Expense", "Food");
			yakshaAssert(currentTest(), false, exceptionTestFile);
		} catch (InvalidAmountException ex) {
			yakshaAssert(currentTest(), true, exceptionTestFile);
		}
	}

	// Test for InvalidAmountException when updating an expense with a non-positive
	// amount
	@Test
	public void testUpdateTransactionWithInvalidAmount() throws IOException, InvalidCategoryException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			Transaction transaction = transactionManager.getAllTransactions().get(0);
			transactionManager.updateTransaction(0, -100.0, "Invalid Update", "Food");
			yakshaAssert(currentTest(), false, exceptionTestFile);
		} catch (InvalidAmountException ex) {
			yakshaAssert(currentTest(), true, exceptionTestFile);
		}
	}
}
