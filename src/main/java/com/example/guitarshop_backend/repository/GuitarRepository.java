package com.example.guitarshop_backend.repository;

import com.example.guitarshop_backend.entity.Guitar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GuitarRepository extends JpaRepository<Guitar, Long> {
    // Chỉ cần để trống thế này thôi!
    // JpaRepository đã làm sẵn cho chúng ta các hàm như:
    // save() - thêm/sửa
    // findAll() - lấy tất cả
    // findById() - tìm theo ID
    // deleteById() - xóa theo ID
    // Tìm đàn có tên chứa từ khóa (Không phân biệt hoa thường)
    List<Guitar> findByNameContainingIgnoreCase(String name);

    // Tìm đàn theo đúng tên thương hiệu (Không phân biệt hoa thường)
    List<Guitar> findByBrandNameIgnoreCase(String brandName);

    // Lọc theo trạng thái (chỉ lấy DRAFT hoặc PUBLISHED)
    Page<Guitar> findByStatus(String status, Pageable pageable);

    // Tìm đàn theo tên và trạng thái
    List<Guitar> findByNameContainingIgnoreCaseAndStatus(String name, String status);

    // Lọc theo thương hiệu và trạng thái
    List<Guitar> findByBrandNameIgnoreCaseAndStatus(String brandName, String status);
}