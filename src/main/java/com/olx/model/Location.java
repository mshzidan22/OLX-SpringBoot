package com.olx.model;

import com.sun.istack.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "LOCATION_ID")
    private Long id;
    private String name;
    private Long parent;
    @OneToMany(mappedBy = "location")
    private Set<Ad> ads = new HashSet<Ad>();
}
