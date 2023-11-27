package com.olx.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;
    private String name;
    private Long parent;
    @OneToMany(mappedBy = "category")
    private Set<Ad> ads;

    public int getCategoryLevel(){
        int[] lv1Category = {6,14,17,20,100,129,138,147,206,223,230,241};
        if (this.parent == 0) return 1;
        else if (Arrays.stream(lv1Category).anyMatch(s -> s == this.parent)) return 2;
        else return 3;
    }
}
