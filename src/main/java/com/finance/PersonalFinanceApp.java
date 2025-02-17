package com.finance;

import java.util.List;
import java.util.Scanner;

import com.finance.exception.InvalidAmountException;
import com.finance.exception.InvalidCategoryException;
import com.finance.inventory.TransactionManager;
import com.finance.models.Transaction;

public class PersonalFinanceApp {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		TransactionManager transactionManager = new TransactionManager();
		double monthlyIncome = 10000; // Default monthly income

		while (true) {
			System.out.println("Options:");
			System.out.println("1. Add Expense");
			System.out.println("2. View All Expenses");
			System.out.println("3. View Expenses by Category");
			System.out.println("4. Update Expense");
			System.out.println("5. Delete Expense");
			System.out.println("6. View Balance");
			System.out.println("7. Generate Monthly Report");
			System.out.println("8. Generate Expense by Category Report");
			System.out.println("9. Set Monthly Income");
			System.out.println("10. Exit");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
			case 1:
				addExpense(transactionManager, scanner);
				break;
			case 2:
				viewAllTransactions(transactionManager);
				break;
			case 3:
				viewTransactionsByCategory(transactionManager, scanner);
				break;
			case 4:
				updateTransaction(transactionManager, scanner);
				break;
			case 5:
				deleteTransaction(transactionManager, scanner);
				break;
			case 6:
				viewBalance(transactionManager, monthlyIncome);
				break;
			case 7:
				generateMonthlyReport(transactionManager, scanner);
				break;
			case 8:
				generateExpenseByCategoryReport(transactionManager);
				break;
			case 9:
				setMonthlyIncome(scanner, monthlyIncome);
				break;
			case 10:
				System.out.println("Exiting...");
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}

	private static void addExpense(TransactionManager transactionManager, Scanner scanner) {
		System.out.print("Enter amount for expense: ");
		double amount = scanner.nextDouble();
		scanner.nextLine(); // Consume newline
		System.out.print("Enter description: ");
		String description = scanner.nextLine();
		System.out.print("Enter category: ");
		String category = scanner.nextLine();

		try {
			transactionManager.addExpense(amount, description, category);
			System.out.println("Expense added successfully.");
		} catch (InvalidAmountException | InvalidCategoryException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void viewAllTransactions(TransactionManager transactionManager) {
		List<Transaction> transactions = transactionManager.getAllTransactions();
		if (transactions.isEmpty()) {
			System.out.println("No transactions found.");
		} else {
			for (Transaction transaction : transactions) {
				System.out.println(transaction);
			}
		}
	}

	private static void viewTransactionsByCategory(TransactionManager transactionManager, Scanner scanner) {
		System.out.print("Enter category: ");
		String category = scanner.nextLine();
		List<Transaction> transactions = transactionManager.getTransactionsByCategory(category);
		if (transactions.isEmpty()) {
			System.out.println("No transactions found for category: " + category);
		} else {
			for (Transaction transaction : transactions) {
				System.out.println(transaction);
			}
		}
	}

	private static void updateTransaction(TransactionManager transactionManager, Scanner scanner) {
		System.out.print("Enter the index of the transaction to update: (index starts at 0) ");
		int index = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		System.out.print("Enter new amount: ");
		double amount = scanner.nextDouble();
		scanner.nextLine(); // Consume newline
		System.out.print("Enter new description: ");
		String description = scanner.nextLine();
		System.out.print("Enter new category: ");
		String category = scanner.nextLine();

		try {
			transactionManager.updateTransaction(index, amount, description, category);
			System.out.println("Transaction updated successfully.");
		} catch (InvalidAmountException | InvalidCategoryException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void deleteTransaction(TransactionManager transactionManager, Scanner scanner) {
		System.out.print("Enter the index of the transaction to delete: ");
		int index = scanner.nextInt();
		scanner.nextLine(); // Consume newline
		transactionManager.removeTransaction(index);
		System.out.println("Transaction deleted successfully.");
	}

	private static void viewBalance(TransactionManager transactionManager, double monthlyIncome) {
		double expenseTotal = 0;

		// Calculate total expenses
		for (Transaction transaction : transactionManager.getAllTransactions()) {
			expenseTotal += transaction.getAmount();
		}

		double netBalance = monthlyIncome - expenseTotal;
		System.out.println("Current Balance: " + netBalance);
	}

	private static void generateMonthlyReport(TransactionManager transactionManager, Scanner scanner) {
		System.out.print("Enter month (1-12): ");
		int month = scanner.nextInt();
		System.out.print("Enter year (YYYY): ");
		int year = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		String report = transactionManager.generateMonthlyReport(month, year);
		System.out.println(report);
	}

	private static void generateExpenseByCategoryReport(TransactionManager transactionManager) {
		String report = transactionManager.generateExpenseByCategoryReport();
		System.out.println(report);
	}

	private static void setMonthlyIncome(Scanner scanner, double monthlyIncome) {
		System.out.print("Enter new monthly income: ");
		monthlyIncome = scanner.nextDouble();
		scanner.nextLine(); // Consume newline

		System.out.println("Monthly income updated to: " + monthlyIncome);
	}
}
