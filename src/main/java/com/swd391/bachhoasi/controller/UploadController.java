package com.swd391.bachhoasi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swd391.bachhoasi.service.CloudStoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
public class UploadController {
    private final CloudStoreService cloudStoreService;
    @PostMapping
    public ResponseEntity<String> uploadFileTest(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(cloudStoreService.save(file));
    }
}
