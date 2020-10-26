package com.olx.util;

import lombok.Data;

@Data
public class CategoryJson {
    private Long category_id;
    private String name;
    private Long parent;
}
