package com.example.guitarshop_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guitars")
public class Guitar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên đàn không được để trống")
    @Column(nullable = false)
    private String name;

    @Min(value = 0, message = "Giá tiền không được là số âm")
    private double price;

    @Min(value = 0, message = "Số lượng tồn kho không được là số âm")
    private int quantity;

    @Column(columnDefinition = "TEXT")
    private String thumbnail; // Ảnh đại diện chính (hỗ trợ cả link cực dài và base64)

    @Column(columnDefinition = "TEXT")
    private String description; // Mô tả chi tiết

    private String woodType; // Loại gỗ
    
    private String color; // Màu sắc

    private Integer stringCount; // Số dây đàn

    @Column(nullable = true)
    private String status = "DRAFT"; // Trạng thái sản phẩm (DRAFT, PUBLISHED)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "guitar", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductImage> images = new ArrayList<>();
}