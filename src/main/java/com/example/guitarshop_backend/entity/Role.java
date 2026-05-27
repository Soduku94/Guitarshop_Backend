package com.example.guitarshop_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tên quyền (VD: ROLE_CUSTOMER, ROLE_ADMIN...)
    @Column(unique = true, nullable = false)
    private String name;
}