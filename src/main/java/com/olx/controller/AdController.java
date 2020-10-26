package com.olx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.olx.assemblers.AdModelAssembler;
import com.olx.converter.AdConverter;
import com.olx.dto.AdDto;
import com.olx.dto.AdInputDto;
import com.olx.dto.MiniAdDto;
import com.olx.dto.UserInfo;
import com.olx.execption.LocationNotFoundException;
import com.olx.model.Account;
import com.olx.model.Ad;
import com.olx.model.Advertiser;
import com.olx.repository.AccountRepository;
import com.olx.repository.AdvertiserRepository;
import com.olx.service.AccountService;
import com.olx.service.AdService;
import com.olx.util.AdIdInput;
import com.olx.util.Schedule;
import com.olx.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
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

       //should be response here
       @GetMapping("/ads/{id}")
        public EntityModel<AdDto> oneAd (@PathVariable("id") Long id) {

        return adModelAssembler.toModel(adService.getAd(id));
        }

        @GetMapping("/ads")
        public CollectionModel<EntityModel<MiniAdDto>> all () {

           return adModelAssembler.toCollectionModel(adService.getHomeAds());
        }


        @GetMapping("/test/{id}")
       public List<Long> test (@PathVariable Long id) throws IOException {
          return utils.getLocationAllChildren(new Long(id));
        }

        @GetMapping("/adding")
        public ResponseEntity<UserInfo> addAd (Principal principal){
            UserInfo userInfo = new UserInfo();
            Optional<Principal> p = Optional.ofNullable(principal);
           if(p.isPresent()){
               Account account =accountService.getAccountByEmail(principal.getName())
                       .orElseThrow(() -> new UsernameNotFoundException("No Such Account"));
                   userInfo.setEmail(account.getEmail());
                   userInfo.setPhone(account.getAdvertiser().getPhone());
                   userInfo.setName(account.getAdvertiser().getName());

           }

            return new ResponseEntity<UserInfo>(userInfo,HttpStatus.OK);
        }

        @PostMapping("/adding")
        public ResponseEntity<EntityModel<AdDto>> createAd(@RequestBody @Valid AdInputDto adInputDto){
                Ad ad= adService.saveAd(adConverter.adInputDtoToAd(adInputDto));
                AdDto adDto = adConverter.entityToDto(ad);
               return new ResponseEntity<EntityModel<AdDto>>(adModelAssembler.toModel(adDto),HttpStatus.CREATED) ;
        }

        @GetMapping("/token")
        public ResponseEntity<String> getImgToken (){
               return new ResponseEntity<String>(schedule.getToken(),HttpStatus.OK);
        }
        //need more work
        @PostMapping("/saveAd")
       public ResponseEntity<?> savaAd (@RequestBody AdIdInput adId, Principal principal ){
            Account account =accountService.getAccountByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("No Such Account"));
            Long advertiserId = account.getAdvertiser().getId();
             adService.saveAdByUser(advertiserId,adId.getAdId());
             return new ResponseEntity<>(HttpStatus.OK);

        }

}
