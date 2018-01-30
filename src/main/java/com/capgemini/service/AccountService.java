package com.capgemini.service;

import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientInitialAmountException;
	
	int depositAmount(int accountNumber, int amount ) throws InvalidAccountNumberException ;
	
	int withDrawAmount(int accountNumber, int amount ) throws InvalidAccountNumberException , InsufficientInitialAmountException ;
}