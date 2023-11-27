package com.olx.dto;


import lombok.Data;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class AdCreationDto {
    @NotNull
    @Size(min = 5,max = 80 ,message = "Title must be more than 5 and less than 80 Chars")
    private String title;
    @NotNull
    @Size(min = 10,max = 4096 ,message = "Title must be more than 10 and less than 4096 Chars")
    private String description;

    private String brand;
    private String condition;
    @Positive(message = "Price should be more than 0 $")
    private Integer price;
    private List<String> images = new ArrayList<>();
    @NotNull(message = "You should Put the Location Id")
    @Positive(message = "LocationId should be positive")
    private Long locationId;
    @NotNull(message = "You should Put the Category Id")
    @Positive(message = "CategoryId should be positive")
    private Long categoryId;
    @NotNull
    @Size(min = 4 , max = 20 , message = "name should not exceed 20 char")
    private String name;
    @NotNull
    @Email(message = "Invalid email format")
    private String email;
    @NotNull
    @Digits(integer = 10, fraction = 0, message = "Invalid phone number format")
    private String phone;
}
