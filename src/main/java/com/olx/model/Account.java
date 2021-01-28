package com.olx.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "ACCOUNT_ID")
    private Long id;
    @Column(unique=true)
    private String email;
    private String password;
    @OneToOne(mappedBy = "account")
    private Advertiser advertiser;
}
