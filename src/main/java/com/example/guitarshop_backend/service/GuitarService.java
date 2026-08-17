package com.example.guitarshop_backend.service;

import com.example.guitarshop_backend.entity.Guitar;
import com.example.guitarshop_backend.entity.Category;
import com.example.guitarshop_backend.entity.Brand;
import com.example.guitarshop_backend.exception.ResourceNotFoundException;
import com.example.guitarshop_backend.repository.GuitarRepository;
import com.example.guitarshop_backend.repository.CategoryRepository;
import com.example.guitarshop_backend.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service // Đánh dấu đây là class xử lý nghiệp vụ
public class GuitarService {

    private final GuitarRepository guitarRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public GuitarService(
            GuitarRepository guitarRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository
    ) {
        this.guitarRepository = guitarRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    public Page<Guitar> getAllGuitars(int page, int size, boolean isAdmin) {
        Pageable pageable = PageRequest.of(page, size);
        if (isAdmin) {
            return guitarRepository.findAll(pageable);
        } else {
            return guitarRepository.findByStatus("PUBLISHED", pageable);
        }
    }

    public Guitar addGuitar(Guitar guitar) {
        if (guitar.getStatus() == null || guitar.getStatus().trim().isEmpty()) {
            guitar.setStatus("DRAFT");
        }
        
        if (guitar.getCategory() != null && guitar.getCategory().getId() != null) {
            Category cat = categoryRepository.findById(guitar.getCategory().getId()).orElse(null);
            guitar.setCategory(cat);
        }
        
        if (guitar.getBrand() != null && guitar.getBrand().getId() != null) {
            Brand brand = brandRepository.findById(guitar.getBrand().getId()).orElse(null);
            guitar.setBrand(brand);
        }
        
        return guitarRepository.save(guitar);
    }

    public Guitar getGuitarById(Long id) {
        return getGuitarById(id, true);
    }

    public Guitar getGuitarById(Long id, boolean isAdmin) {
        Guitar guitar = guitarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đàn với ID: " + id));
        if (!isAdmin && !"PUBLISHED".equalsIgnoreCase(guitar.getStatus())) {
            throw new ResourceNotFoundException("Không tìm thấy đàn với ID: " + id);
        }
        return guitar;
    }

    public Guitar updateGuitar(Long id, Guitar guitarDetails) {
        Guitar guitar = getGuitarById(id, true);

        guitar.setName(guitarDetails.getName());
        guitar.setPrice(guitarDetails.getPrice());
        guitar.setQuantity(guitarDetails.getQuantity());
        guitar.setThumbnail(guitarDetails.getThumbnail());
        guitar.setDescription(guitarDetails.getDescription());
        guitar.setWoodType(guitarDetails.getWoodType());
        guitar.setColor(guitarDetails.getColor());
        guitar.setStringCount(guitarDetails.getStringCount());
        
        if (guitarDetails.getCategory() != null && guitarDetails.getCategory().getId() != null) {
            Category cat = categoryRepository.findById(guitarDetails.getCategory().getId()).orElse(null);
            guitar.setCategory(cat);
        } else {
            guitar.setCategory(null);
        }
        
        if (guitarDetails.getBrand() != null && guitarDetails.getBrand().getId() != null) {
            Brand brand = brandRepository.findById(guitarDetails.getBrand().getId()).orElse(null);
            guitar.setBrand(brand);
        } else {
            guitar.setBrand(null);
        }

        if (guitarDetails.getStatus() != null && !guitarDetails.getStatus().trim().isEmpty()) {
            guitar.setStatus(guitarDetails.getStatus());
        }

        return guitarRepository.save(guitar);
    }

    public void deleteGuitar(Long id) {
        guitarRepository.deleteById(id);
    }

    public List<Guitar> searchByName(String keyword, boolean isAdmin) {
        if (isAdmin) {
            return guitarRepository.findByNameContainingIgnoreCase(keyword);
        } else {
            return guitarRepository.findByNameContainingIgnoreCaseAndStatus(keyword, "PUBLISHED");
        }
    }

    public List<Guitar> getByBrand(String brand, boolean isAdmin) {
        if (isAdmin) {
            return guitarRepository.findByBrandNameIgnoreCase(brand);
        } else {
            return guitarRepository.findByBrandNameIgnoreCaseAndStatus(brand, "PUBLISHED");
        }
    }
}