package com.kata.bank_account.services;

import java.time.LocalDateTime;

import javax.money.MonetaryAmount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kata.bank_account.models.Customer;
import com.kata.bank_account.utils.Printer;

/**
 * @author fahmi
 *
 */
@Service
public class AccountService {
	
	@Autowired
	TransactionService transactionService;

	public AccountService(){
		
	}
	
	public void depositMoney(Customer customer, MonetaryAmount value, LocalDateTime date) {
		transactionService.registerTransaction(customer, value, date, "Deposit");
	}
	
	public void retrieveMoney(Customer customer, MonetaryAmount value, LocalDateTime date) {
		transactionService.registerTransaction(customer, value, date, "Withdrawl");
	}
	
	public void printHistoryTransactions(Printer printer, Customer customer) {
		transactionService.printHistory(printer, customer);
	}
	
}
