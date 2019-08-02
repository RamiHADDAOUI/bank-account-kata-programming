package com.kata.bank_account.models;

import java.time.LocalDateTime;

import javax.money.MonetaryAmount;

/**
 * @author fahmi
 *
 */
public class Account {

	private LocalDateTime creationDate;
	private MonetaryAmount amount;

	public Account() {
		super();
	}

	public Account(LocalDateTime creationDate, MonetaryAmount amount) {
		super();
		this.creationDate = creationDate;
		this.amount = amount;
	}

	public MonetaryAmount getAmount() {
		return amount;
	}

	public void setAmount(MonetaryAmount amount) {
		this.amount = amount;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

}
