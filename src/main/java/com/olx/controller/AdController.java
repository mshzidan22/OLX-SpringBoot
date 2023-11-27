package com.olx.controller;

import com.olx.assemblers.AdModelAssembler;
import com.olx.converter.AdConverter;
import com.olx.dto.AdCreationDto;
import com.olx.dto.AdDto;
import com.olx.dto.MiniAdDto;
import com.olx.dto.UserInfo;
import com.olx.model.Account;
import com.olx.model.Ad;
import com.olx.service.AccountService;
import com.olx.service.AdService;
import com.olx.util.AdIdInput;
import com.olx.util.ImgToken;
import com.olx.util.Schedule;
import com.olx.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class AdController {
    @Autowired
    private AdService adService;
    @Autowired
    private AdModelAssembler adModelAssembler;
    @Autowired
    private AccountService accountService;
    @Autowired
    private Utils utils;
    @Autowired
    private Schedule schedule;
    @Autowired
    private AdConverter adConverter;
    @Autowired
    private ImgToken imgToken;

    //should be response here
    @GetMapping("/ads/{id}")
    public EntityModel<AdDto> getAd(@PathVariable("id") Long id) {

        return adModelAssembler.toModel(adService.getAd(id));
    }

    @GetMapping("/home")
    public CollectionModel<EntityModel<MiniAdDto>> all() {

        return adModelAssembler.toCollectionModel(adService.getHomeAds());
    }





    @GetMapping("/adding")
    public ResponseEntity<UserInfo> addAd(Principal principal) {
        UserInfo userInfo = new UserInfo();
        Optional<Principal> p = Optional.ofNullable(principal);
        if (p.isPresent()) {
            Account account = accountService.getAccountByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("No Such Account"));
            userInfo.setEmail(account.getEmail());
            userInfo.setPhone(account.getAdvertiser().getPhone());
            userInfo.setName(account.getAdvertiser().getName());
        }
        return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
    }

    @PostMapping("/adding")
    public ResponseEntity<EntityModel<AdDto>> createAd(@RequestBody @Valid AdCreationDto adCreationDto) {
        System.out.println("start adding");
        Ad ad = adService.saveAd(adConverter.adCreationDtoToAd(adCreationDto));
        AdDto adDto = adConverter.entityToDto(ad);
        return new ResponseEntity<EntityModel<AdDto>>(adModelAssembler.toModel(adDto), HttpStatus.CREATED);
    }



    @GetMapping("/token")
    public ResponseEntity<ImgToken> getImgToken() {
        return new ResponseEntity<ImgToken>(imgToken, HttpStatus.OK);
    }

    //need more work
    @PostMapping("/saveAd")
    public ResponseEntity<?> saveAd(@RequestBody AdIdInput adId, Principal principal) {
        Account account = accountService.getAccountByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("No Such Account"));
        Long advertiserId = account.getAdvertiser().getId();
        adService.saveAdByUser(advertiserId, adId.getAdId());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/canEdit/{adId}/{userId}")
    public Boolean canEdit(@PathVariable Long adId, @PathVariable Long userId) {
        return adService.IsAdBelongsToUser(adId, userId);
    }

    @DeleteMapping("/unSave/{adId}")
    public ResponseEntity<?> unSaveAd(@PathVariable Long adId, Principal principal) {
        Account account = accountService.getAccountByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("No Such Account"));
        Long advertiserId = account.getAdvertiser().getId();
        adService.unSaveAd(adId, advertiserId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    //return only Ids
    @GetMapping("/mySavedAds")
    public ResponseEntity<List<Long>> getSavedAdsByUser(Principal principal) {
        Account account = accountService.getAccountByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("No Such Account"));
        Long advertiserId = account.getAdvertiser().getId();
        List<Long> ads = adService.getSavedAdsByUser(advertiserId);
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    @GetMapping("myaccount/savedAds")
    public ResponseEntity<List<MiniAdDto>> getSavedAds(Principal principal) throws IOException {
        Account account = accountService.getAccountByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("No Such Account"));
        List<Ad> saved = account.getAdvertiser().getSaved().stream().collect(Collectors.toList());
        List<MiniAdDto> savedList = adConverter.listEntityToDto((ArrayList<Ad>) saved);
        return new ResponseEntity<>(savedList, HttpStatus.OK);
    }



}
