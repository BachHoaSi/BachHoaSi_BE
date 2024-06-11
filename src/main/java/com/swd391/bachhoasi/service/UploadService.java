package com.swd391.bachhoasi.service;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    String uploadProductImage(BigDecimal id, MultipartFile file);
}
