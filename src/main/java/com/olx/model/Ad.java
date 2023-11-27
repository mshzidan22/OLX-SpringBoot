package com.olx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
@NoArgsConstructor
@Entity
//@JsonIgnoreProperties({"category","location","advertiser" , "savedBy" ,"img"})
//parent entity is that does not containing mapped by and cascading operation puts on parent
// if one to many you should put cascade in one to many relation only
@Table(name = "AD")
public class Ad {
    @Id
    @GeneratedValue
    @Column(name = "AD_ID")
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String title;
    @Column(nullable = false, length = 4096)
    @NotBlank
    private String description;
    private Long views;
    private String condition;
    private String brand;
    private Integer price;
    private LocalDateTime time;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "ADVERTISER_ID")
    private Advertiser advertiser;
    @ManyToMany(mappedBy = "saved")
    private Set<Advertiser> savedBy = new HashSet<>();
    @ElementCollection
    @JoinColumn(name = "AD_ID")
    private Set<Img> images = new HashSet<>();

}

