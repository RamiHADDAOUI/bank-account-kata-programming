package com.kata.bank_account.services;

import java.time.LocalDateTime;

import javax.money.MonetaryAmount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kata.bank_account.models.Customer;
import com.kata.bank_account.models.Transaction;
import com.kata.bank_account.utils.Printer;

/**
 * @author fahmi
 *
 */
@Service
public class TransactionService {
	
	@Autowired
	Printer printer;

	public TransactionService() {

	}

	public void registerTransaction(Customer customer, MonetaryAmount amount, LocalDateTime date, String operation) {
		MonetaryAmount currentAmount = customer.getAccount().getAmount();
		MonetaryAmount newAmount = currentAmount;
		if (amount.isNegative()) {
			printer.printError("Impossible Transaction : Negative Amount");
			return;
		}
		if ("Deposit".equals(operation))
			newAmount = currentAmount.add(amount);
		else if ("Withdrawl".equals(operation)) {
			if (amount.isGreaterThan(currentAmount)) {
				printer.printError("Impossible Transaction : Not Enough Money");
				return;
			}
			newAmount = currentAmount.subtract(amount);
		}
		Transaction transaction = new Transaction(amount, newAmount, date, operation);
		customer.getAccount().setAmount(newAmount);
		customer.getTransactions().add(transaction);
	}

}
