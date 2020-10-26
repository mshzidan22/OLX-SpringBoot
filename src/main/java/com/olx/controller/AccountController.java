package com.olx.controller;


import com.olx.assemblers.AdModelAssembler;
import com.olx.dto.AdDto;
import com.olx.dto.AdInputDto;
import com.olx.dto.AdUserDto;
import com.olx.dto.MiniAdDto;
import com.olx.model.Account;
import com.olx.model.Ad;
import com.olx.service.AccountService;
import com.olx.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    @Autowired
    private AdService adService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AdModelAssembler adModelAssembler;

    @GetMapping("/myaccount")
    public ResponseEntity<List<EntityModel<AdUserDto>>> myAccount (Principal principal){
        Account account=accountService.getAccountByEmail(principal.getName()).orElseThrow(RuntimeException::new);
        List<AdUserDto> adList = adService.getAllAdsByAdvertiser(account.getAdvertiser().getId());
        List<EntityModel<AdUserDto>> listResponse=adList.stream().map(adModelAssembler::toAdUserDtoModel).collect(Collectors.toList());

        return new ResponseEntity<List<EntityModel<AdUserDto>>>(listResponse,HttpStatus.OK);


    }

    @DeleteMapping("/myaccount/ad/{id}")
    public ResponseEntity<?> deleteAd (@PathVariable Long id){
        adService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }



}
