package com.olx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Advertiser {
    @Id
    @GeneratedValue
    @Column(name = "ADVERTISER_ID")
    private Long id;
    private String name;
    private String phone;
    @OneToMany(mappedBy = "advertiser",cascade = CascadeType.REMOVE)
    private Set<Ad> ads = new HashSet<Ad>();
    @ManyToMany
    @JoinTable(name = "SAVED_ADS",joinColumns = { @JoinColumn(name = "ADVERTISER_ID") },
           inverseJoinColumns = { @JoinColumn(name = "AD_ID") })
    private Set<Ad> saved = new HashSet<Ad>();
    @OneToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
}
