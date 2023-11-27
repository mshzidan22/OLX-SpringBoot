package com.olx.dto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
public class AccountRegistrationDto {
    @NotNull
    @Size(min = 5,max = 25 ,message = "Name should be between 6 and 25 char")
    private String name;
    @NotNull
    @Size(min = 3,max = 25 ,message = "Password must be more than 3 char")
    private String password;
    @NotNull
    @Email(message = "Enter correct email address")
    private String email;
    @NotNull
    @Digits(integer = 10,fraction = 0 , message = "Enter correct phone")
    private String phone;
}
