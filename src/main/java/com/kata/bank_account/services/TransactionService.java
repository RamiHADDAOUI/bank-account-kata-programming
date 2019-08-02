package com.kata.bank_account.services;

import java.time.LocalDateTime;

import javax.money.MonetaryAmount;

import org.springframework.stereotype.Service;

import com.kata.bank_account.models.Customer;
import com.kata.bank_account.models.Transaction;

/**
 * @author fahmi
 *
 */
@Service
public class TransactionService {

	public TransactionService() {

	}

	public void registerTransaction(Customer customer, MonetaryAmount amount, LocalDateTime date) {
		MonetaryAmount newAmount = amount.add(customer.getAccount().getAmount());
		Transaction transaction = new Transaction(amount, newAmount, date, "Deposit");
		customer.getAccount().setAmount(newAmount);
		customer.getTransactions().add(transaction);
	}

}
