package com.kata.bank_account.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fahmi
 *
 */
public class Customer {

	private String name;
	private Account account;
	private List<Transaction> transactions;

	public Customer() {
		super();
	}

	public Customer(String name, Account account) {
		super();
		this.name = name;
		this.account = account;
		this.transactions = new ArrayList<Transaction>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
