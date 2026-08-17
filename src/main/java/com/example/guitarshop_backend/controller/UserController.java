package com.example.guitarshop_backend.controller;

import com.example.guitarshop_backend.dto.UserDTO;
import com.example.guitarshop_backend.dto.UserResponseDTO;
import com.example.guitarshop_backend.dto.ChangePasswordRequest;
import com.example.guitarshop_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Lấy thông tin cá nhân của người dùng hiện tại
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // Cập nhật thông tin cá nhân của người dùng hiện tại
    @PutMapping("/profile")
    public ResponseEntity<UserResponseDTO> updateProfile(Authentication authentication, @RequestBody UserDTO userDTO) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.updateUserProfile(email, userDTO));
    }

    // Đổi mật khẩu
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(Authentication authentication, @RequestBody ChangePasswordRequest request) {
        String email = authentication.getName();
        userService.changePassword(email, request);
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }

    // Lấy danh sách tất cả người dùng (Chỉ dành cho Admin)
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Lấy thông tin 1 người dùng bằng ID (Admin)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Cập nhật thông tin người dùng bằng ID (Admin)
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    // Khóa (Xóa mềm) người dùng (Admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Đã khóa tài khoản người dùng thành công");
    }
}
