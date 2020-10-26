package com.olx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Img {
    @Column(length = 1024)
    private String imgSrc;
}
