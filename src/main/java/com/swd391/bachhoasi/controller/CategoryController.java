package com.swd391.bachhoasi.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swd391.bachhoasi.model.dto.request.CategoryRequest;
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
    @GetMapping
    public ResponseEntity<ResponseObject> getPageCategory(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, 
    
    @RequestParam(name = "q", required = false
    ) String query) {
        var result = categoryService.getAllCategory();
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("GET_CATEGORY_SUCCESS")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .message("Query Page Success")
            .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategoryDetail(@PathVariable(value = "id") BigDecimal id){
        var result = categoryService.getCategoryDetail(id);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("GET_CATEGORY_DETAIL_SUCCESS")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .message("Query Success")
            .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ResponseObject> addNewCategory(@RequestBody CategoryRequest request
    ) {
        var result = categoryService.insertCategory(request);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("ADD_CATEGORY_SUCCESS")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .message("Create Success")
            .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable(name = "id") BigDecimal id, @RequestBody CategoryRequest request
    ) {
        var result = categoryService.updateCategory(id,request);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("UPDATE_CATEGORY_SUCCESS")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .message("Update Success")
            .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ResponseObject> disableCategory(@PathVariable(name = "id") BigDecimal id
    ) {
        var result = categoryService.removeCategory(id);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("DISABLE_CATEGORY_SUCCESS")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .message("Disable Category Success")
            .build()
        );
    }
}