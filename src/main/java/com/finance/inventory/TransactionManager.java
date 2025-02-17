package com.finance.inventory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.exception.InvalidAmountException;
import com.finance.exception.InvalidCategoryException;
import com.finance.models.Transaction;

public class TransactionManager {

	private List<Transaction> transactions; // Store all transactions
	private Map<String, Double> categoryBudgets; // Store budgets for each category (not used anymore)

	// Constructor
	public TransactionManager() {
		transactions = new ArrayList<>();
		categoryBudgets = new HashMap<>();
	}

	// Method to add a new expense
	public void addExpense(double amount, String description, String category)
			throws InvalidAmountException, InvalidCategoryException {
		if (amount <= 0) {
			throw new InvalidAmountException("Expense amount must be positive.");
		}

		// If the category doesn't exist, create a new one (no budget check needed)
		if (!categoryBudgets.containsKey(category)) {
			categoryBudgets.put(category, 0.0); // Default category with no budget
		}

		// Add the expense to the list of transactions
		Transaction expense = new Transaction(amount, new java.util.Date(), description, category);
		transactions.add(expense);
	}

	// Method to get the total expenses for a category
	private double getTotalExpensesForCategory(String category) {
		double totalExpense = 0;
		for (Transaction transaction : transactions) {
			if (transaction.getCategory().equals(category)) {
				totalExpense += transaction.getAmount();
			}
		}
		return totalExpense;
	}

	// Method to update an existing transaction (expense)
	public void updateTransaction(int index, double amount, String description, String category)
			throws InvalidAmountException, InvalidCategoryException {
		if (index < 0 || index >= transactions.size()) {
			throw new IndexOutOfBoundsException("Transaction not found at index: " + index);
		}

		Transaction transaction = transactions.get(index);

		// Validation checks
		if (amount <= 0) {
			throw new InvalidAmountException("Amount must be positive.");
		}
		if (!categoryBudgets.containsKey(category)) {
			throw new InvalidCategoryException("Invalid category: " + category);
		}

		// Update transaction
		transaction.setAmount(amount);
		transaction.setDescription(description);
		transaction.setCategory(category);
	}

	// Method to remove a transaction (expense)
	public void removeTransaction(int index) {
		if (index < 0 || index >= transactions.size()) {
			throw new IndexOutOfBoundsException("Transaction not found at index: " + index);
		}
		transactions.remove(index);
	}

	// Method to get the list of all transactions (expenses)
	public List<Transaction> getAllTransactions() {
		return transactions;
	}

	// Method to get the list of all transactions (expenses) for a specific category
	public List<Transaction> getTransactionsByCategory(String category) {
		List<Transaction> filteredTransactions = new ArrayList<>();
		for (Transaction transaction : transactions) {
			if (transaction.getCategory().equals(category)) {
				filteredTransactions.add(transaction);
			}
		}
		return filteredTransactions;
	}

	// Method to get the balance (income minus total expenses)
	public double getBalance(double monthlyIncome) {
		double expenseTotal = 0;

		for (Transaction transaction : transactions) {
			expenseTotal += transaction.getAmount();
		}

		return monthlyIncome - expenseTotal;
	}

	// Method to generate monthly expenses report
	public String generateMonthlyReport(int month, int year) {
		double totalExpense = 0;

		for (Transaction transaction : transactions) {
			// Ensure you're working with LocalDate for proper month and year comparison
			LocalDate transactionDate = LocalDate.of(transaction.getDate().getYear() + 1900,
					transaction.getDate().getMonth() + 1, transaction.getDate().getDate());

			// Compare month and year
			if (transactionDate.getMonthValue() == month && transactionDate.getYear() == year) {
				totalExpense += transaction.getAmount();
			}
		}

		return "Month: " + month + " " + year + "\nTotal Expenses: " + totalExpense;
	}

	// Method to generate expense report by category
	public String generateExpenseByCategoryReport() {
		Map<String, Double> categoryExpenses = new HashMap<>();
		for (Transaction transaction : transactions) {
			categoryExpenses.put(transaction.getCategory(),
					categoryExpenses.getOrDefault(transaction.getCategory(), 0.0) + transaction.getAmount());
		}

		StringBuilder report = new StringBuilder("Expense by Category:\n");
		for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
			report.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
		}
		return report.toString();
	}
}
