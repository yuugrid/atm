package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.resource.RequestAmount;
import com.example.resource.ResponseAmount;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    public Account insert(RequestAmount requestAmount) {
    	Account account = new Account();
    	account.setAmount(0);
    	return this.accountRepository.save(account);
    }

    public ResponseAmount getResponseAmount(Integer accountId) {
        Account account = accountRepository.findById(accountId).get();
        ResponseAmount responseAmount = new ResponseAmount();
        responseAmount.setAmount(account.getAmount());
        return responseAmount;
    }
    
    public ResponseAmount depositAccount(Integer accountId, RequestAmount requestAmount) {
    	Account account = accountRepository.findById(accountId).get();
    	account.setAmount(requestAmount.getAmount() + account.getAmount());
    	account = accountRepository.save(account);
    	
    	ResponseAmount depositBalance = new ResponseAmount();
    	depositBalance.setAmount(account.getAmount());
    	return depositBalance;
    }
    
    public ResponseAmount withdrawAccount(Integer accountId, RequestAmount requestAmount) {
    	Account account = accountRepository.findById(accountId).get();
    	if(requestAmount.getAmount() <= account.getAmount()) {
    		account.setAmount(account.getAmount() - requestAmount.getAmount());
    		accountRepository.save(account);
    	}
    	
    	ResponseAmount depositBalance = new ResponseAmount();
    	depositBalance.setAmount(account.getAmount());
    	return depositBalance;
    }
}
