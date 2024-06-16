package com.swd391.bachhoasi.service.impl;

import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.dto.response.CategoryResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.repository.CategoryRepository;
import com.swd391.bachhoasi.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public PaginationResponse<CategoryResponse> getAllCategory() {
        return new PaginationResponse<>(categoryRepository.findAll().stream().map(item -> CategoryResponse.builder()
                .code(item.getCategoryCode())
                .description(item.getDescription())
                .id(item.getId())
                .name(item.getName())
                .build()).toList());
    }

}
