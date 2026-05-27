package com.example.guitarshop_backend.repository;

import com.example.guitarshop_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Tìm quyền theo tên (Để xíu nữa gán quyền mặc định cho khách hàng mới)
    Optional<Role> findByName(String name);
}