package com.capgemini.service;

import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	
	AccountRepository accountRepository;
	
	
	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}


	
	public Account createAccount(int accountNumber,int amount) throws InsufficientInitialAmountException
	{
		int a,b,c;
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		
		account.setAmount(amount);
		 
		if(accountRepository.save(account))
		{
			return account;
		}
	     
		return null;
		
	}


	public int depositAmount(int accountNumber, int amount)
			throws InvalidAccountNumberException {
		// TODO Auto-generated method stub
		Account acc = accountRepository.searchAccount(accountNumber) ; 
		if(acc == null){
			throw new InvalidAccountNumberException() ;
		}
		int sumAmount = acc.getAmount() + amount;
		acc.setAmount(sumAmount);
		//accountRepository.save(acc);
		return sumAmount;
	}


	public int withDrawAmount(int accountNumber, int amount)
			throws InvalidAccountNumberException,
			InsufficientInitialAmountException {
		Account acc = accountRepository.searchAccount(accountNumber) ; 
		if(acc == null){
			throw new InvalidAccountNumberException() ;
		}
		System.out.println("Withdraw account "+amount );
		System.out.println("Withdraw account "+acc.getAmount()  );
		if(amount > acc.getAmount() ){
			throw new InsufficientInitialAmountException();
		}
		acc.setAmount( acc.getAmount() - amount );
	
		// TODO Auto-generated method stub
		return ( acc.getAmount());
	}

}
