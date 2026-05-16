package com.example.guitarshop_backend.repository;

import com.example.guitarshop_backend.entity.Guitar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuitarRepository extends JpaRepository<Guitar, Long> {
    // Chỉ cần để trống thế này thôi!
    // JpaRepository đã làm sẵn cho chúng ta các hàm như:
    // save() - thêm/sửa
    // findAll() - lấy tất cả
    // findById() - tìm theo ID
    // deleteById() - xóa theo ID
}