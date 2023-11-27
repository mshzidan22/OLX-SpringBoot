package com.olx.service;

import com.olx.dto.AccountRegistrationDto;
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

    public Advertiser saveAdvertiser (AccountRegistrationDto accountRegistrationDto){
        if(accountRepository.findByEmail(accountRegistrationDto.getEmail()).isPresent()){
            throw new DuplicateEmailException("Duplicate Email ");
        }
        Account account = new Account();
        account.setEmail(accountRegistrationDto.getEmail());
        account.setPassword(accountRegistrationDto.getPassword());
        Advertiser advertiser = new Advertiser();
        advertiser.setAccount(account);
        advertiser.setName(accountRegistrationDto.getName());
        advertiser.setPhone(accountRegistrationDto.getPhone());
     return    advertiserRepository.save(advertiser);
    }

    public Advertiser getAdvertiserByEmail(String email){
        return advertiserRepository.findByAccountEmail(email).get();
    }



}
