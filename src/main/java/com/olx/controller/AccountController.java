package com.olx.controller;


import com.olx.assemblers.AdModelAssembler;
import com.olx.converter.AdConverter;
import com.olx.dto.*;
import com.olx.model.Account;
import com.olx.model.Ad;
import com.olx.model.Img;
import com.olx.service.AccountService;
import com.olx.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @Autowired
    private AdConverter adConverter;

    @GetMapping("/myaccount")
    public ResponseEntity<List<EntityModel<AdUserDto>>> myAccount (Principal principal){
        Account account=accountService.getAccountByEmail(principal.getName()).orElseThrow(RuntimeException::new);
        List<AdUserDto> adList = adService.getAllAdsByAdvertiser(account.getAdvertiser().getId());
        List<EntityModel<AdUserDto>> listResponse=adList.stream().map(adModelAssembler::toAdUserDtoModel).collect(Collectors.toList());

        return new ResponseEntity<>(listResponse,HttpStatus.OK);


    }

    @DeleteMapping("/myaccount/ad/{id}")
    public ResponseEntity<?> deleteAd (@PathVariable Long id){
        adService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/myaccount/ad/{id}")
    public ResponseEntity<?> replaceAd (@RequestBody UpdatedAdDto updatedAdDto, @PathVariable Long id){
       Ad updatedAd= adService.findAd(id).map(ad -> {
            ad.setTitle(updatedAdDto.getTitle());
            ad.setCondition(updatedAdDto.getCondition());
            ad.setPrice(updatedAdDto.getPrice());
            ad.setDescription(updatedAdDto.getDescription());
            ad.setBrand(updatedAdDto.getBrand());
            ad.setImages(updatedAdDto.getImages().stream().map(a-> new Img(a)).collect(Collectors.toSet()));
            return adService.saveAd(ad);

        }).orElseThrow(()->new RuntimeException("hi"));

         EntityModel<AdDto> entityModel  = adModelAssembler.toModel(adConverter.entityToDto(updatedAd));
         return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                 .body(entityModel);
    }

    @PutMapping("/myaccount/settings")
    public ResponseEntity<?> modifyAccount (Principal principal , @RequestBody @Valid AccountDto accountDto){
        Account account =accountService.getAccountByEmail(principal.getName())
                .map(account1 -> {
                    account1.setEmail(accountDto.getEmail());
                    account1.setPassword(accountDto.getPassword());
                    account1.getAdvertiser().setName(accountDto.getName());
                    account1.getAdvertiser().setPhone(accountDto.getPhone());
                    return accountService.save(account1);
                })
                .orElseThrow(() -> new UsernameNotFoundException("No Such Account"));

        return new ResponseEntity<>(HttpStatus.CREATED);




    }





}
