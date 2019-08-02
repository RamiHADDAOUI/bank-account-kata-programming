package com.kata.bank_account.utils;

/**
 * @author fahmi
 *
 */
public interface Printer {
	public void print(String textToPrint);
	public void printf(String format, Object ...args);
	public void printError(String textToPrint);
}
