package com.olx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.olx.model.Img;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AdDto extends MiniAdDto {
    private String description;
    private Long views;
    private String condition;
    private String brand;
    private String advertiserPhone;
    private String advertiserName;
    private Set<String> images= new HashSet<>();
    private Long advertiserId;
    private String addedAt;
    private Set<RelevantAdDto> relevantAdDto =new HashSet<>();
    @JsonIgnore
    private String img;

}
