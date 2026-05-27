package com.example.guitarshop_backend.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserResponseDTO {
    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private boolean active;
    private Set<String> roles;
}
