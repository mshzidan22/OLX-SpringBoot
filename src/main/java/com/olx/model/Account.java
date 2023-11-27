package com.olx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "ACCOUNT_ID")
    private Long id;
    @Column(unique=true)
    @NotBlank
    private String email;

    private String password;
    @OneToOne(mappedBy = "account")
    private Advertiser advertiser;
}
