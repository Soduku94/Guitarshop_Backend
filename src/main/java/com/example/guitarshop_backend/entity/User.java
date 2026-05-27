package com.example.guitarshop_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email; // Dùng email để đăng nhập

    @Column(nullable = false)
    private String password; // Mật khẩu (sau này sẽ mã hóa)

    private String fullName;

    private String phone;

    private boolean active = true; // Trạng thái tài khoản (Mặc định là đang hoạt động)

    // Quan hệ Nhiều - Nhiều: 1 User có nhiều Role, 1 Role có thể gắn cho nhiều User
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "user_id"), // Cột nối với bảng users
            inverseJoinColumns = @JoinColumn(name = "role_id") // Cột nối với bảng roles
    )
    private Set<Role> roles = new HashSet<>();
}