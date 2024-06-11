package com.swd391.bachhoasi.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.swd391.bachhoasi.model.entity.Product;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.repository.ProductRepository;
import com.swd391.bachhoasi.service.CloudStoreService;
import com.swd391.bachhoasi.service.UploadService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {
    private final ProductRepository productRepository;
    private final CloudStoreService cloudStoreService;

    @Override
    public String uploadProductImage(BigDecimal id, MultipartFile file) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Not found product with Id: %s", id.toString())));
        String url = cloudStoreService.save(file);
        product.setUrlImages(url);
        try {
            productRepository.save(product);
        }catch (Exception ex){
            throw new ActionFailedException(String.format("Product Image Upload failed for reason: %s", ex.toString()));
        }
        return url;
    }
    
}
