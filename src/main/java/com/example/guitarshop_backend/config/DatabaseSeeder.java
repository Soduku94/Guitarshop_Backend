package com.example.guitarshop_backend.config;

import com.example.guitarshop_backend.entity.*;
import com.example.guitarshop_backend.repository.RoleRepository;
import com.example.guitarshop_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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

    @Autowired
    private com.example.guitarshop_backend.repository.GuitarRepository guitarRepository;

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

        // Tạo Đàn Guitar mặc định
        if (guitarRepository.count() == 0) {
            Category acoustic = categoryRepository.findAll().stream().filter(c -> "acoustic".equals(c.getSlug())).findFirst().orElse(null);
            Category electric = categoryRepository.findAll().stream().filter(c -> "electric".equals(c.getSlug())).findFirst().orElse(null);

            Brand fender = brandRepository.findAll().stream().filter(b -> "Fender".equalsIgnoreCase(b.getName())).findFirst().orElse(null);
            Brand gibson = brandRepository.findAll().stream().filter(b -> "Gibson".equalsIgnoreCase(b.getName())).findFirst().orElse(null);
            Brand martin = brandRepository.findAll().stream().filter(b -> "Martin".equalsIgnoreCase(b.getName())).findFirst().orElse(null);
            Brand taylor = brandRepository.findAll().stream().filter(b -> "Taylor".equalsIgnoreCase(b.getName())).findFirst().orElse(null);

            guitarRepository.save(new Guitar(null, "Fender Stratocaster American Professional II", 45000000.0, 5, "https://images.unsplash.com/photo-1564186763535-ebb21ef5277f?q=80&w=2070&auto=format&fit=crop", "Độ hoàn thiện tinh xảo từ Mỹ.", "Alder", "3-Color Sunburst", 6, "PUBLISHED", acoustic, fender, new java.util.ArrayList<>()));
            guitarRepository.save(new Guitar(null, "Gibson Les Paul Standard '50s", 68000000.0, 2, "https://images.unsplash.com/photo-1550291652-6cb90046361f?q=80&w=1964&auto=format&fit=crop", "Âm thanh dầy dặn cổ điển đặc trưng Gibson.", "Mahogany", "Heritage Cherry Sunburst", 6, "PUBLISHED", electric, gibson, new java.util.ArrayList<>()));
            guitarRepository.save(new Guitar(null, "Martin D-28 Acoustic", 75000000.0, 3, "https://images.unsplash.com/photo-1510915361894-db8b60106cb1?q=80&w=2070&auto=format&fit=crop", "Huyền thoại dòng nhạc dân gian và acoustic.", "Spruce/Rosewood", "Natural", 6, "PUBLISHED", acoustic, martin, new java.util.ArrayList<>()));
            guitarRepository.save(new Guitar(null, "Taylor 814ce Builder's Edition", 82000000.0, 1, "https://images.unsplash.com/photo-1550985616-10810253b84d?q=80&w=1924&auto=format&fit=crop", "Thiết kế cao cấp bậc nhất dòng grand auditorium.", "Rosewood/Spruce", "Natural Satin", 6, "PUBLISHED", acoustic, taylor, new java.util.ArrayList<>()));
            System.out.println("Đã khởi tạo 4 cây đàn Guitar mặc định trong database.");
        }
    }
}
