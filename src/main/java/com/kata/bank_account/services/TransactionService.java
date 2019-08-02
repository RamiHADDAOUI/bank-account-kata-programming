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

	public void registerTransaction(Customer customer, MonetaryAmount amount, LocalDateTime date, String operation) {
		MonetaryAmount currentAmount = customer.getAccount().getAmount();
		MonetaryAmount newAmount = currentAmount;
		if ("Deposit".equals(operation))
			newAmount = currentAmount.add(amount);
		else if ("Withdrawl".equals(operation)) {
			newAmount = currentAmount.subtract(amount);
		}
		Transaction transaction = new Transaction(amount, newAmount, date, operation);
		customer.getAccount().setAmount(newAmount);
		customer.getTransactions().add(transaction);
	}

}
