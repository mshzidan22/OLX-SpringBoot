package com.olx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class UserInfo {
    private static final long serialVersionUID= 1L;
    private String name;
    private String Email;
    private String phone;
}
