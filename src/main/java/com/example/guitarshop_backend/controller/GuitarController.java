package com.example.guitarshop_backend.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.guitarshop_backend.entity.Guitar;
import com.example.guitarshop_backend.service.GuitarService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@CrossOrigin
@RequestMapping("/api/guitars")
@Tag(name = "Guitar Management", description = "Các API dùng để quản lý kho đàn Guitar") // Đặt tên nhóm API
public class GuitarController {

    private final GuitarService guitarService;

    // Inject Service thay vì Repository
    public GuitarController(GuitarService guitarService) {
        this.guitarService = guitarService;
    }

    private boolean checkIfAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
        }
        return false;
    }

    @Operation(summary = "Lấy danh sách đàn (Có phân trang)", description = "Trả về danh sách tất cả các loại đàn hiện có trong kho")
    @GetMapping
    public Page<Guitar> getAllGuitars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return guitarService.getAllGuitars(page, size, checkIfAdmin());
    }

    @Operation(summary = "Thêm một cây đàn mới")
    @PostMapping
    public Guitar addGuitar(@Valid @RequestBody Guitar guitar) {
        return guitarService.addGuitar(guitar);
    }
    
    @Operation(summary = "Lấy chi tiết đàn theo ID")
    @GetMapping("/{id}")
    public Guitar getGuitarById(@PathVariable("id") Long id) { // Thêm ("id")
        return guitarService.getGuitarById(id, checkIfAdmin());
    }

    @PutMapping("/{id}")
    public Guitar updateGuitar(@PathVariable Long id, @Valid @RequestBody Guitar guitarDetails) {
        return guitarService.updateGuitar(id, guitarDetails);
    }

    @Operation(summary = "Xóa một cây đàn")
    @DeleteMapping("/{id}")
    public String deleteGuitar(@PathVariable("id") Long id) { // Thêm ("id")
        guitarService.deleteGuitar(id);
        return "Đã xóa thành công cây đàn có ID: " + id;
    }
    
    // API Tìm kiếm theo tên (VD: /api/guitars/search?name=classic)
    @Operation(summary = "Tìm kiếm đàn theo tên")
    @GetMapping("/search")
    public List<Guitar> searchGuitars(@RequestParam("name") String name) { // Thêm ("name")
        return guitarService.searchByName(name, checkIfAdmin());
    }
    
    // API Lọc theo thương hiệu (VD: /api/guitars/brand?name=Yamaha)
    @Operation(summary = "Lọc đàn theo thương hiệu")
    @GetMapping("/brand")
    public List<Guitar> getGuitarsByBrand(@RequestParam("name") String name) { // Thêm ("name")
        return guitarService.getByBrand(name, checkIfAdmin());
    }

}