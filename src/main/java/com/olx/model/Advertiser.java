package com.olx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    private String phone;
    @OneToMany(mappedBy = "advertiser",orphanRemoval = true)
    private Set<Ad> ads = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.DETACH ,CascadeType.PERSIST })
    @JoinTable(name = "SAVED_ADS",joinColumns = { @JoinColumn(name = "ADVERTISER_ID") },
           inverseJoinColumns = { @JoinColumn(name = "AD_ID") })
    @JsonIgnore
    private Set<Ad> saved = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;



}
