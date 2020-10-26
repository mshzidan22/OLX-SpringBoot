package com.olx.dto;

import lombok.Data;

@Data
public class MiniAdDto {

    private static final long serialVersionUID= 1L;
    private Long id;
    private String title;
    private String img;
    private Integer price;
    private String addedAt;
    private String gov;
    private String city;
    private CategoryDto categoryDto;
}
