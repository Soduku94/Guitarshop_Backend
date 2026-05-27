package com.example.guitarshop_backend.service;

import com.example.guitarshop_backend.dto.UserDTO;
import com.example.guitarshop_backend.dto.UserResponseDTO;
import com.example.guitarshop_backend.entity.Role;
import com.example.guitarshop_backend.entity.User;
import com.example.guitarshop_backend.repository.RoleRepository;
import com.example.guitarshop_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFullName(userDTO.getFullName());
        user.setPhone(userDTO.getPhone());
        user.setActive(true);

        // Gắn role mặc định là ROLE_CUSTOMER
        Optional<Role> roleOpt = roleRepository.findByName("ROLE_CUSTOMER");
        Role role;
        if (roleOpt.isPresent()) {
            role = roleOpt.get();
        } else {
            role = new Role();
            role.setName("ROLE_CUSTOMER");
            role = roleRepository.save(role);
        }
        
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + id));
        return mapToResponse(user);
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với email: " + email));
        return mapToResponse(user);
    }

    public UserResponseDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + id));

        user.setFullName(userDTO.getFullName());
        user.setPhone(userDTO.getPhone());
        // Email và Password có thể cần API riêng để cập nhật cho an toàn
        
        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + id));
        user.setActive(false); // Xóa mềm (Soft delete)
        userRepository.save(user);
    }

    private UserResponseDTO mapToResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setActive(user.isActive());
        
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        dto.setRoles(roleNames);
        
        return dto;
    }
}
