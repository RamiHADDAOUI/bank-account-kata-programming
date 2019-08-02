package com.kata.bank_account;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;
import java.time.Month;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kata.bank_account.models.Account;
import com.kata.bank_account.models.Customer;
import com.kata.bank_account.services.AccountService;
import com.kata.bank_account.utils.ConsolePrinter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-config.xml" })
public class AccountTest {
	
	private static final CurrencyUnit EURO = Monetary.getCurrency("EUR");
	
	@Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	
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
		MonetaryAmount amountRetrieved = Money.of(1.275, EURO);
		accountService.retrieveMoney(customer, amountRetrieved, LocalDateTime.now());
		assertThat(customer.getAccount().getAmount().getNumber().doubleValue(), is(1.225));
	}
	
	@Test
	public void testRetrieveMoneyWithNegativeAmount() {
		MonetaryAmount amountRetrieved = Money.of(-1.275, EURO);
		int numberOfTransactionsBeforeRetrieve = customer.getTransactions().size();
		accountService.retrieveMoney(customer, amountRetrieved, LocalDateTime.now());
		int numberOfTransactionsAfterRetrieve = customer.getTransactions().size();
		assertThat(numberOfTransactionsBeforeRetrieve, is(numberOfTransactionsAfterRetrieve));
		assertThat(systemErrRule.getLog(), is("Impossible Transaction : Negative Amount"));
	}
	
	@Test
	public void testRetrieveMoneyWithValueGreaterThanAmount() {
		MonetaryAmount amountRetrieved = Money.of(5.50, EURO);
		int numberOfTransactionsBeforeRetrieve = customer.getTransactions().size();
		accountService.retrieveMoney(customer, amountRetrieved, LocalDateTime.now());
		int numberOfTransactionsAfterRetrieve = customer.getTransactions().size();
		assertThat(numberOfTransactionsBeforeRetrieve, is(numberOfTransactionsAfterRetrieve));
		assertThat(systemErrRule.getLog(), is("Impossible Transaction : Not Enough Money"));
	}
	
	@Test
	public void testPrintHistoryTransactions() {
		MonetaryAmount amountDeposited_1 = Money.of(1.2500, EURO);
		MonetaryAmount amountDeposited_2 = Money.of(2.5000, EURO);
		MonetaryAmount amountDeposited_3 = Money.of(1.2000, EURO);
		MonetaryAmount amountRetrived_1  = Money.of(1.3000, EURO);
		MonetaryAmount amountRetrived_2  = Money.of(1.3000, EURO);

		accountService.depositMoney(customer, amountDeposited_1, LocalDateTime.of(2017, Month.MARCH, 05, 12, 50, 00));
		accountService.depositMoney(customer, amountDeposited_2, LocalDateTime.of(2017, Month.SEPTEMBER, 14, 02, 30, 30));
		accountService.retrieveMoney(customer, amountRetrived_1, LocalDateTime.of(2018, Month.MAY, 01, 06, 10, 40));
		accountService.depositMoney(customer, amountDeposited_3, LocalDateTime.of(2018, Month.DECEMBER, 22, 22, 10, 55));
		accountService.retrieveMoney(customer, amountRetrived_2, LocalDateTime.of(2019, Month.JANUARY, 03, 10, 15, 15));
		
		accountService.printHistoryTransactions(new ConsolePrinter() , customer);
		
		String displayedText =
"            Customer            Operation                 Date               Amount              Balance\r\n"+
"-------------------------------------------------------------------------------------------------------------------\r\n"+
"     Fahmi BEN SALAH              Deposit  2017-03-05 12:50:00               1,25 €               3,75 €\r\n"+
"     Fahmi BEN SALAH              Deposit  2017-09-14 02:30:30               2,50 €               6,25 €\r\n"+
"     Fahmi BEN SALAH            Withdrawl  2018-05-01 06:10:40               1,30 €               4,95 €\r\n"+
"     Fahmi BEN SALAH              Deposit  2018-12-22 22:10:55               1,20 €               6,15 €\r\n"+
"     Fahmi BEN SALAH            Withdrawl  2019-01-03 10:15:15               1,30 €               4,85 €\r\n";

		assertThat(systemOutRule.getLog(), is(displayedText));
	}
}
