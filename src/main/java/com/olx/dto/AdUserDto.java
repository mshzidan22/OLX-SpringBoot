package com.olx.dto;

import lombok.Data;

@Data
public class AdUserDto {
    private Long id;
    private String title;
    private String addedAt;
    private Integer price;
    private Integer views;
}
