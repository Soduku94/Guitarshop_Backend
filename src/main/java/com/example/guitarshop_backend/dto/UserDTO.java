package com.example.guitarshop_backend.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String password;
    private String fullName;
    private String phone;
}
