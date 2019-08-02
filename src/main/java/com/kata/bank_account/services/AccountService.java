package com.kata.bank_account.services;

import java.time.LocalDateTime;

import javax.money.MonetaryAmount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kata.bank_account.models.Customer;

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
		transactionService.registerTransaction(customer, value, date);
	}
	
}
