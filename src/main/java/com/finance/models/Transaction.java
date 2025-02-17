package com.finance.models;

import java.util.Date;

public class Transaction {
	private double amount;
	private Date date;
	private String description;
	private String category;

	// Constructor
	public Transaction(double amount, Date date, String description, String category) {
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.category = category;
	}

	// Getters and Setters
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	// toString method for displaying transaction details
	@Override
	public String toString() {
		return "Amount: " + amount + ", Date: " + date + ", Description: " + description + ", Category: " + category;
	}
}
