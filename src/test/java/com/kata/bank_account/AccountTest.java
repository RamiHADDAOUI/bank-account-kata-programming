package com.kata.bank_account;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
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
	
	private static final CurrencyUnit EURO = Monetary.getCurrency("EUR");
	
	@Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();
	
	@Autowired
	private AccountService accountService;
	
	private Customer customer;
	private Account account;

	@Before()
	public void setup() {
		MonetaryAmount initialAmount = Money.of(2.50, EURO);
		account = new Account(LocalDateTime.now(), initialAmount);
		customer = new Customer("Fahmi BEN SALAH", account);
	}

	@Test
	public void testDepositMoney() {
		MonetaryAmount amountDeposited = Money.of(1.275, EURO);
		accountService.depositMoney(customer, amountDeposited, LocalDateTime.now());
		assertThat(customer.getAccount().getAmount().getNumber().doubleValue(), is(3.775));
	}
	
	@Test
	public void testDepositMoneyWithNegativeAmount() {
		MonetaryAmount amountDeposited = Money.of(-1.275, EURO);
		int numberOfTransactionsBeforeDeposit = customer.getTransactions().size();
		accountService.depositMoney(customer, amountDeposited, LocalDateTime.now());
		int numberOfTransactionsAfterDeposit = customer.getTransactions().size();
		assertThat(numberOfTransactionsBeforeDeposit, is(numberOfTransactionsAfterDeposit));
		assertThat(systemErrRule.getLog(), is("Impossible Transaction : Negative Amount"));
	}
	
	@Test
	public void testRetrieveMoney() {
		MonetaryAmount amountDeposited = Money.of(1.275, EURO);
		accountService.retrieveMoney(customer, amountDeposited, LocalDateTime.now());
		assertThat(customer.getAccount().getAmount().getNumber().doubleValue(), is(1.225));
	}
	
	@Test
	public void testRetrieveMoneyWithNegativeAmount() {
		MonetaryAmount amountDeposited = Money.of(-1.275, EURO);
		int numberOfTransactionsBeforeRetrieve = customer.getTransactions().size();
		accountService.retrieveMoney(customer, amountDeposited, LocalDateTime.now());
		int numberOfTransactionsAfterRetrieve = customer.getTransactions().size();
		assertThat(numberOfTransactionsBeforeRetrieve, is(numberOfTransactionsAfterRetrieve));
		assertThat(systemErrRule.getLog(), is("Impossible Transaction : Negative Amount"));
	}
	
	@Test
	public void testRetrieveMoneyWithValueGreaterThanAmount() {
		MonetaryAmount amountDeposited = Money.of(5.50, EURO);
		int numberOfTransactionsBeforeRetrieve = customer.getTransactions().size();
		accountService.retrieveMoney(customer, amountDeposited, LocalDateTime.now());
		int numberOfTransactionsAfterRetrieve = customer.getTransactions().size();
		assertThat(numberOfTransactionsBeforeRetrieve, is(numberOfTransactionsAfterRetrieve));
		assertThat(systemErrRule.getLog(), is("Impossible Transaction : Not Enough Money"));
	}
}
