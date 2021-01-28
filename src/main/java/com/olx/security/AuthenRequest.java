package com.olx.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenRequest {
    private String username;
    private String password;

    public AuthenRequest() {
    }
}
