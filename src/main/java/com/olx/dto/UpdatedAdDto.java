package com.olx.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
@Data
public class UpdatedAdDto {
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
    private ArrayList<String> images = new ArrayList<>();

}
