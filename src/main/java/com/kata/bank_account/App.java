package com.kata.bank_account;

import java.time.LocalDateTime;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kata.bank_account.models.Account;
import com.kata.bank_account.models.Customer;
import com.kata.bank_account.services.AccountService;

/**
 * @author fahmi
 *
 */
public class App {
	
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("spring-config.xml");
		AccountService accountService = (AccountService) context.getBean("accountService");
		MonetaryAmount initialAmount = Monetary.getDefaultAmountFactory().setNumber(3.45).setCurrency("EUR").create();
		Account account = new Account(LocalDateTime.now(), initialAmount);
		Customer customer = new Customer("Fahmi BEN SALAH", account);
		MonetaryAmount savedAmount = Monetary.getDefaultAmountFactory().setNumber(2.255).setCurrency("EUR").create();
		accountService.depositMoney(customer, savedAmount, LocalDateTime.now());
		System.out.println(customer.getAccount().getAmount());
	}

}
