package com.example.guitarshop_backend.service;

import com.example.guitarshop_backend.entity.Guitar;
import com.example.guitarshop_backend.exception.ResourceNotFoundException;
import com.example.guitarshop_backend.repository.GuitarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service // Đánh dấu đây là class xử lý nghiệp vụ
public class GuitarService {

    private final GuitarRepository guitarRepository;

    public GuitarService(GuitarRepository guitarRepository) {
        this.guitarRepository = guitarRepository;
    }

    public Page<Guitar> getAllGuitars(int page, int size) {
        // Tạo đối tượng Pageable để quy định trang số mấy, mỗi trang bao nhiêu phần tử
        Pageable pageable = PageRequest.of(page, size);
        return guitarRepository.findAll(pageable); // Trả về Page thay vì List
    }
    public Guitar addGuitar(Guitar guitar) {
        return guitarRepository.save(guitar);
    }
    public Guitar getGuitarById(Long id) {
        return guitarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đàn với ID: " + id));
    }



    public Guitar updateGuitar(Long id, Guitar guitarDetails) {
        // Tái sử dụng luôn hàm tìm kiếm ở trên để code gọn hơn
        Guitar guitar = getGuitarById(id);

        guitar.setName(guitarDetails.getName());
        guitar.setBrand(guitarDetails.getBrand());
        guitar.setPrice(guitarDetails.getPrice());
        guitar.setQuantity(guitarDetails.getQuantity());

        return guitarRepository.save(guitar);
    }

    public void deleteGuitar(Long id) {
        guitarRepository.deleteById(id);
    }
    // Thêm vào dưới cùng của GuitarService
    public List<Guitar> searchByName(String keyword) {
        return guitarRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Guitar> getByBrand(String brand) {
        return guitarRepository.findByBrandIgnoreCase(brand);
    }
}