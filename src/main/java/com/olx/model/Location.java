package com.olx.model;

import com.sun.istack.Nullable;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
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
