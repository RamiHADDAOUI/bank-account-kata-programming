package com.kata.bank_account.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.format.CurrencyStyle;
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
	
	private static final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
	private static final MonetaryAmountFormat AMOUNT_FORMAT = MonetaryFormats.getAmountFormat(
		      AmountFormatQueryBuilder.of(Locale.FRANCE)
		        .set(CurrencyStyle.SYMBOL)
		        .build()
		    );
	
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
	
	public void printHistory(Printer printer, Customer customer) {
		printer.printf("%20s %20s %20s %20s %20s", "Customer", "Operation", "Date", "Amount", "Balance");
		System.out.println();
		printer.print(
				"-------------------------------------------------------------------------------------------------------------------");
		System.out.println();
		printTransactions(printer, customer);
	}

	private void printTransactions(Printer printer, Customer customer) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
		StringBuilder sb = new StringBuilder();
		for (Transaction transaction : customer.getTransactions()) {
			printer.printf("%20s %20s %20s %20s %20s", customer.getName(), transaction.getOperation(),
					transaction.getDate().format(formatter), AMOUNT_FORMAT.format(transaction.getAmount()), AMOUNT_FORMAT.format(transaction.getBalance()));
			sb.setLength(0);
			System.out.println();
		}
	}

}
