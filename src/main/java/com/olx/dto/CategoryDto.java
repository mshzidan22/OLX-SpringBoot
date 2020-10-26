package com.olx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {
    private String categoryLv1;
    private String categoryLv2;
    private String categoryLv3;
}
