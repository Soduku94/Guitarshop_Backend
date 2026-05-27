package com.example.guitarshop_backend.config;

import com.example.guitarshop_backend.entity.Role;
import com.example.guitarshop_backend.entity.User;
import com.example.guitarshop_backend.repository.RoleRepository;
import com.example.guitarshop_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import com.example.guitarshop_backend.entity.Category;
import com.example.guitarshop_backend.entity.Brand;
import com.example.guitarshop_backend.repository.CategoryRepository;
import com.example.guitarshop_backend.repository.BrandRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void run(String... args) throws Exception {
        // Tạo Role ADMIN nếu chưa có
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("ROLE_ADMIN");
            return roleRepository.save(newRole);
        });

        // Tạo Role CUSTOMER nếu chưa có
        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("ROLE_CUSTOMER");
            return roleRepository.save(newRole);
        });

        // Tạo tài khoản Admin mặc định
        if (!userRepository.existsByEmail("admin@guitarshop.com")) {
            User admin = new User();
            admin.setEmail("admin@guitarshop.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setFullName("Quản Trị Viên");
            admin.setPhone("0987654321");
            admin.setActive(true);

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepository.save(admin);
            System.out.println("Đã khởi tạo tài khoản Admin mặc định (admin@guitarshop.com / 123456)");
        }

        // Tạo Danh mục mặc định
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category(null, "Đàn Acoustic", "acoustic", "Đàn guitar acoustic phù hợp đệm hát."));
            categoryRepository.save(new Category(null, "Đàn Classic", "classic", "Đàn guitar classic dây nylon."));
            categoryRepository.save(new Category(null, "Đàn Electric", "electric", "Đàn guitar điện."));
            categoryRepository.save(new Category(null, "Đàn Bass", "bass", "Đàn guitar bass trầm."));
        }

        // Tạo Thương hiệu mặc định
        if (brandRepository.count() == 0) {
            brandRepository.save(new Brand(null, "Yamaha", "https://logowik.com/content/uploads/images/yamaha.jpg", "Nhật Bản"));
            brandRepository.save(new Brand(null, "Fender", "https://logowik.com/content/uploads/images/fender.jpg", "Mỹ"));
            brandRepository.save(new Brand(null, "Taylor", "https://logowik.com/content/uploads/images/taylor-guitars.jpg", "Mỹ"));
            brandRepository.save(new Brand(null, "Martin", "https://logowik.com/content/uploads/images/martin-co.jpg", "Mỹ"));
            brandRepository.save(new Brand(null, "Gibson", "https://logowik.com/content/uploads/images/gibson-guitar.jpg", "Mỹ"));
        }
    }
}
