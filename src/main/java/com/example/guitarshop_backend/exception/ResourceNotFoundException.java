package com.example.guitarshop_backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message); // Truyền lời nhắn lỗi lên class cha
    }
}