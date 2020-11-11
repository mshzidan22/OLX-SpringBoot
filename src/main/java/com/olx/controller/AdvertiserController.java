package com.olx.controller;

import com.olx.dto.AccountDto;
import com.olx.model.Advertiser;
import com.olx.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AdvertiserController {
    @Autowired
    private AdvertiserService advertiserService;

    @PostMapping("/register")
    public ResponseEntity<Advertiser> createAccount (@RequestBody @Valid AccountDto accountDto){
       Advertiser advertiser =advertiserService.saveAdvertiser(accountDto);
        return new ResponseEntity<>(advertiser,HttpStatus.CREATED);
    }




}
