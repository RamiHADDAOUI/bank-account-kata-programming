package com.kata.bank_account;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kata.bank_account.models.Account;
import com.kata.bank_account.models.Customer;
import com.kata.bank_account.services.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-config.xml" })
public class AccountTest {
	
	@Autowired
	private AccountService accountService;
	
	private Customer customer;
	private Account account;

	@Before()
	public void setup() {
		MonetaryAmount initialAmount = Monetary.getDefaultAmountFactory().setNumber(2.50).setCurrency("EUR").create();
		account = new Account(LocalDateTime.now(), initialAmount);
		customer = new Customer("Fahmi BEN SALAH", account);
	}

	@Test
	public void testDepositMoney() {
		MonetaryAmount amountDeposited = Monetary.getDefaultAmountFactory().setNumber(1.275).setCurrency("EUR").create();
		accountService.depositMoney(customer, amountDeposited, LocalDateTime.now());
		assertThat(customer.getAccount().getAmount().getNumber().doubleValue(), is(3.775));
	}
	
}
