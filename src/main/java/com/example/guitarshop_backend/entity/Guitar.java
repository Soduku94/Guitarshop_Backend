package com.example.guitarshop_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "guitars")
public class Guitar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên đàn không được để trống")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Thương hiệu không được để trống")
    private String brand;

    @Min(value = 0, message = "Giá tiền không được là số âm")
    private double price;

    @Min(value = 0, message = "Số lượng tồn kho không được là số âm")
    private int quantity;
}