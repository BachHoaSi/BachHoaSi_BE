package com.swd391.bachhoasi.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.CloudStoreService;
import com.swd391.bachhoasi.service.UploadService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
public class UploadController {
    private final CloudStoreService cloudStoreService;
    private final UploadService uploadService;
    @PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFileTest(@RequestPart MultipartFile file) {
        return ResponseEntity.ok(cloudStoreService.save(file));
    }
    @PostMapping(value = "/products/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> uploadImage(@PathVariable(value = "id") BigDecimal id, @RequestPart MultipartFile file) {
        String url = uploadService.uploadProductImage(id, file);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("UPLOAD_PRODUCT_IMAGE_SUCCESS")
            .data(url)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .build()
        );
    }
    
}
