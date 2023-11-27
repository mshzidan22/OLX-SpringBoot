package com.olx.controller;

import com.olx.dto.AccountRegistrationDto;
import com.olx.execption.IncorrectUserOrPassException;
import com.olx.model.Advertiser;
import com.olx.security.AuthenRequest;
import com.olx.security.AuthenResponse;
import com.olx.security.JwtUtil;
import com.olx.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin

public class AdvertiserController {
    @Autowired
    private AdvertiserService advertiserService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private  JwtUtil jwtUtil;



    @PostMapping("/register")
    public ResponseEntity<Advertiser> createAccount (@RequestBody @Valid AccountRegistrationDto accountRegistrationDto){
       Advertiser advertiser =advertiserService.saveAdvertiser(accountRegistrationDto);
        return new ResponseEntity<>(advertiser,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken (@RequestBody AuthenRequest authenRequest) throws Exception {
        try {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenRequest
        .getUsername(),authenRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new IncorrectUserOrPassException("incorrect Email or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        Advertiser advertiser = advertiserService.getAdvertiserByEmail(authenRequest.getUsername());
        return ResponseEntity.ok(new AuthenResponse(jwt ,advertiser.getId(),advertiser.getName(), advertiser.getPhone(),authenRequest.getUsername()));



    }









}
