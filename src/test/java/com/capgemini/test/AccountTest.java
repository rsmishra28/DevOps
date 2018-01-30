package com.capgemini.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
public class AccountTest {

	AccountService accountService;
	
	@Mock
	AccountRepository accountRepository;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}

	/*
	 * create account
	 * 1.when the amount is less than 500 then system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialAmountException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 5000));
	}

	/*
	 * deposit Money
	 * 1)check Account exist
	 * 2)deposit amount
	 * */
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void checkAccountExistWhileDepositMoney() throws  InvalidAccountNumberException
	{
		
		when(accountRepository.searchAccount(5000)).thenReturn(null);
		assertEquals(null, accountService.depositAmount(5000, 101));
	}
	
	@Test
	public void checkTotalAccountExistWhileDepositMoney() throws  InvalidAccountNumberException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.searchAccount(101)).thenReturn(account);
		
		int totalAccount = accountService.depositAmount(101, 100);
		System.out.println(" totalAccount :"+ totalAccount);
		int expectedAmount = 5100 ;
		assertEquals(expectedAmount , totalAccount );
		
	}
	/*
	 * Check amount while withdraw money
	 * 
	 * */
	

	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void checkExcessAccountWhilewithDrawMoney() throws  InvalidAccountNumberException, InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		int amountExces = accountService.withDrawAmount(101, 8000);	
	
	}
	
	
	@Test
	public void checkTotalAccountWhilewithDrawMoney() throws  InvalidAccountNumberException, InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		int amountExces = accountService.withDrawAmount(101, 1000);	
		System.out.println( " Withdrat money "+ amountExces);
		assertEquals(4000, amountExces);
	}
	
	
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void checkAccountExistWhilewithDrawMoney() throws  InvalidAccountNumberException, InsufficientInitialAmountException
	{
		when(accountRepository.searchAccount(5000)).thenReturn(null);
		assertEquals(null, accountService.withDrawAmount(5000, 101));	
	}
	
	

}
