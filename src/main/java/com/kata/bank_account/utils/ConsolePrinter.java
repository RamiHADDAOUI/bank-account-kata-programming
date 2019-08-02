package com.kata.bank_account.utils;

import java.io.PrintStream;

import org.springframework.stereotype.Component;

/**
 * @author fahmi
 *
 */
@Component
public class ConsolePrinter implements Printer {

	public void print(String textToPrint) {
		PrintStream printer = System.out;
		printer.println(textToPrint);
	}

	public void printError(String textToPrint) {
		PrintStream printer = System.err;
		printer.println(textToPrint);
	}

	public void printf(String format, Object ...args) {
		PrintStream printer = System.out;
		printer.printf(format, args);
	}

}
