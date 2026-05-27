package com.example.guitarshop_backend.repository;

import com.example.guitarshop_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm user bằng email (Dùng khi đăng nhập)
    Optional<User> findByEmail(String email);

    // Kiểm tra xem email đã có ai đăng ký chưa
    boolean existsByEmail(String email);
}