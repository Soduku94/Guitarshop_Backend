package com.example.guitarshop_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "guitars") // Tên bảng trong database
public class Guitar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Khóa chính, tự động tăng

    @Column(nullable = false)
    private String name; // Tên đàn

    private String brand; // Thương hiệu (Yamaha, Taylor, Fender...)

    private double price; // Giá tiền

    private int quantity; // Số lượng tồn kho
}