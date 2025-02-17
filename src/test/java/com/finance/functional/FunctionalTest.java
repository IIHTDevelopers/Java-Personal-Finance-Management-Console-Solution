package com.finance.functional;

import static com.finance.testutils.TestUtils.businessTestFile;
import static com.finance.testutils.TestUtils.currentTest;
import static com.finance.testutils.TestUtils.testReport;
import static com.finance.testutils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.finance.inventory.TransactionManager;
import com.finance.models.Transaction;

public class FunctionalTest {

	private TransactionManager transactionManager;

	@BeforeEach
	public void setUp() {
		transactionManager = new TransactionManager();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testAddExpense() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			yakshaAssert(currentTest(), transactionManager.getAllTransactions().size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetAllTransactions() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			List<Transaction> transactions = transactionManager.getAllTransactions();
			yakshaAssert(currentTest(), transactions.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetTransactionsByCategory() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			transactionManager.addExpense(200.0, "Snacks", "Food");
			List<Transaction> transactions = transactionManager.getTransactionsByCategory("Food");
			yakshaAssert(currentTest(), transactions.size() == 2, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testUpdateTransaction() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			Transaction updatedTransaction = transactionManager.getAllTransactions().get(0);
			transactionManager.updateTransaction(0, 600.0, "Updated Groceries", "Food");
			Transaction updated = transactionManager.getAllTransactions().get(0);
			yakshaAssert(currentTest(), updated != null && updated.getAmount() == 600.0, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testRemoveTransaction() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			transactionManager.removeTransaction(0);
			yakshaAssert(currentTest(), transactionManager.getAllTransactions().size() == 0, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetBalance() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			double balance = transactionManager.getBalance(10000);
			yakshaAssert(currentTest(), balance == 9500, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGenerateMonthlyReport() throws IOException {
		try {
			// Add an expense
			transactionManager.addExpense(500.0, "Groceries", "Food");

			// Get current month and year
			int currentMonth = LocalDate.now().getMonthValue(); // Get the current month (1-based)
			int currentYear = LocalDate.now().getYear(); // Get the current year (4-digit)

			// Generate the report for the current month and year
			String report = transactionManager.generateMonthlyReport(currentMonth, currentYear);

			// Assert that the report contains the correct total expense for the current
			// month
			yakshaAssert(currentTest(), report.contains("Total Expenses: 500.0"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGenerateExpenseByCategoryReport() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			transactionManager.addExpense(200.0, "Snacks", "Food");
			String report = transactionManager.generateExpenseByCategoryReport();
			yakshaAssert(currentTest(), report.contains("Food: 700.0"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}
}
