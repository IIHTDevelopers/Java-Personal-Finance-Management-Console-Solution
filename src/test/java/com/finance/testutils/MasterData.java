package com.finance.testutils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finance.models.Transaction;

public class MasterData {

	private static final Random random = new Random();

	public static Transaction getTransaction() {
		return new Transaction(generateRandomAmount(), generateRandomDate(), "Mock Description " + random.nextInt(100),
				"Mock Category " + random.nextInt(10));
	}

	public static List<Transaction> getTransactionList() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(getTransaction());
		return transactions;
	}

	private static double generateRandomAmount() {
		return random.nextDouble() * 1000; // Generates a random amount between 0 and 1000
	}

	private static Date generateRandomDate() {
		long currentTime = System.currentTimeMillis();
		long randomTime = currentTime + random.nextInt(10000000); // Generate a random future date
		return new Date(randomTime);
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			final String jsonContent = mapper.writeValueAsString(obj);

			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
