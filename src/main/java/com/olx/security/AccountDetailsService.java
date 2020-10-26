package com.olx.security;

import com.olx.model.Account;
import com.olx.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



public class AccountDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Account account= accountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No such User"));
       return new AccountDetails(account);

    }
}
