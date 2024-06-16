package com.swd391.bachhoasi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllCategory() {
        var result = categoryService.getAllCategory();
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("GET_CATEGORY_SUCCESS")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .message("Query Success")
            .build()
        );
    }
}
