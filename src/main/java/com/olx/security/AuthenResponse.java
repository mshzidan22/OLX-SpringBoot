package com.olx.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class AuthenResponse {
    private final String jwt;
    private Long id ;
    private String username;
    private String phone;
    private String email;

}
