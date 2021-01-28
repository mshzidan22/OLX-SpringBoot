package com.olx.service;

import com.olx.dto.AccountDto;
import com.olx.execption.DuplicateEmailException;
import com.olx.model.Account;
import com.olx.model.Advertiser;
import com.olx.repository.AccountRepository;
import com.olx.repository.AdvertiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertiserService {
    @Autowired
    private AdvertiserRepository advertiserRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Advertiser saveAdvertiser (AccountDto accountDto){
        if(accountRepository.findByEmail(accountDto.getEmail()).isPresent()){
            throw new DuplicateEmailException("DuplicateEmailException");
        }
        Account account = new Account();
        account.setEmail(accountDto.getEmail());
        account.setPassword(accountDto.getPassword());
        Advertiser advertiser = new Advertiser();
        advertiser.setAccount(account);
        advertiser.setName(accountDto.getName());
        advertiser.setPhone(accountDto.getPhone());
     return    advertiserRepository.save(advertiser);
    }

    public Advertiser getAdvertiserByEmail(String email){
        return advertiserRepository.findByAccountEmail(email).get();
    }



}
