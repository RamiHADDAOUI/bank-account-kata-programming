package com.kata.bank_account;

import java.time.LocalDateTime;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kata.bank_account.models.Account;
import com.kata.bank_account.models.Customer;
import com.kata.bank_account.services.AccountService;
import com.kata.bank_account.utils.ConsolePrinter;

/**
 * @author fahmi
 *
 */
public class App {
	
	private static final CurrencyUnit EURO = Monetary.getCurrency("EUR");
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("spring-config.xml");
		AccountService accountService = (AccountService) context.getBean("accountService");
		MonetaryAmount initialAmount = Money.of(3.45, EURO);
		Account account = new Account(LocalDateTime.now(), initialAmount);
		Customer customer = new Customer("Fahmi BEN SALAH", account);
		MonetaryAmount amountDeposited = Money.of(2.255, EURO);
		accountService.depositMoney(customer, amountDeposited, LocalDateTime.now());
		MonetaryAmount amountRetrieved = Money.of(1.775, EURO);
		accountService.retrieveMoney(customer, amountRetrieved, LocalDateTime.now());
		accountService.printHistoryTransactions(new ConsolePrinter(), customer);
	}

}
