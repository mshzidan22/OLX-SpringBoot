package com.olx.service;

import com.olx.model.Account;
import com.olx.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);

    }

    public Account save(Account account){
        return accountRepository.save(account);
    }
}