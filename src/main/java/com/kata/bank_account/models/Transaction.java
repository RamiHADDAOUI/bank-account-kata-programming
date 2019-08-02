package com.kata.bank_account.models;


import java.time.LocalDateTime;

import javax.money.MonetaryAmount;

/**
 * @author fahmi
 *
 */
public class Transaction {

	private MonetaryAmount amount;
	private MonetaryAmount balance;
	private LocalDateTime date;
	private String operation;

	public Transaction() {
		super();
	}

	public Transaction(MonetaryAmount amount, MonetaryAmount balance, LocalDateTime date, String operation) {
		super();
		this.amount = amount;
		this.balance = balance;
		this.date = date;
		this.operation = operation;
	}

	public MonetaryAmount getAmount() {
		return amount;
	}

	public void setAmount(MonetaryAmount amount) {
		this.amount = amount;
	}

	public MonetaryAmount getBalance() {
		return balance;
	}

	public void setBalance(MonetaryAmount balance) {
		this.balance = balance;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}
