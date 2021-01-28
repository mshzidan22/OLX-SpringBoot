package com.olx.dto;

import lombok.Data;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
public class AccountDto {
    @NotNull
    @Size(min = 5,max = 25 ,message = "name should be between 6 and 25 char")
    private String name;
    @NotNull
    @Size(min = 3,max = 25 ,message = "password must be more than 3 char")
    private String password;
    @NotNull
    @Email(message = "enter correct email address")
    private String email;
    @NotNull
    @Digits(integer = 10,fraction = 0 , message = "enter correct phone")
    private String phone;
}
