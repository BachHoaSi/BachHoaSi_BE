package com.swd391.bachhoasi.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.dto.request.CategoryRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.CategoryDetailResponse;
import com.swd391.bachhoasi.model.dto.response.CategoryResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.entity.Category;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.repository.CategoryRepository;
import com.swd391.bachhoasi.service.CategoryService;
import com.swd391.bachhoasi.util.AuthUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final AuthUtils authUtils;

    @Override
    public PaginationResponse<CategoryResponse> getAllCategory() {
        return new PaginationResponse<>(categoryRepository.findAll().stream().map(item -> CategoryResponse.builder()
                .code(item.getCategoryCode())
                .description(item.getDescription())
                .id(item.getId())
                .name(item.getName())
                .build()).toList());
    }
    @Override
    public PaginationResponse<CategoryResponse> getPaginationCategory(SearchRequestParamsDto search) {
        var data = categoryRepository.searchAnyByParameter(search.search(), search.pagination())
                .map(item -> CategoryResponse.builder()
                        .code(item.getCategoryCode())
                        .description(item.getDescription())
                        .id(item.getId())
                        .name(item.getName())
                        .build());
        return new PaginationResponse<>(data);
    }
    @Override
    public CategoryDetailResponse getCategoryDetail(BigDecimal id) {
        var data = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Not found category with id: %s", id.toString())));
        var createdBy = data.getCreatedBy();
        var updatedBy = data.getUpdatedBy();
        return CategoryDetailResponse.builder()
                .code(data.getCategoryCode())
                .description(data.getDescription())
                .id(data.getId())
                .name(data.getName())
                .createBy(createdBy == null ? null
                        : new CategoryDetailResponse().new UserCategory(createdBy.getId(), createdBy.getFullName(),
                                createdBy.getRole().toString()))
                .updateBy(updatedBy == null ? null
                        : new CategoryDetailResponse().new UserCategory(updatedBy.getId(), updatedBy.getFullName(),
                                updatedBy.getRole().toString()))
                .createdDate(data.getCreatedDate())
                .updatedDate(data.getUpdatedDate())
                .build();
    }

    @Override
    public CategoryResponse insertCategory(CategoryRequest request) {
        var loginUser = authUtils.getAdminUserFromAuthentication();
        Category categoryDb = Category.builder()
                .categoryCode(request.getCode())
                .description(request.getDescription())
                .name(request.getName())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .build();
        try {
            var item = categoryRepository.save(categoryDb);
            return CategoryResponse.builder()
                    .code(item.getCategoryCode())
                    .description(item.getDescription())
                    .id(item.getId())
                    .name(item.getName())
                    .build();
        } catch (Exception ex) {
            throw new ActionFailedException(
                    String.format("Insert category failed with with reason: %s", ex.getMessage()));
        }
    }

    @Override
    public CategoryResponse updateCategory(BigDecimal id, CategoryRequest request) {
        var temp = categoryRepository.findByCategoryCodeAndIdNot(request.getCode(), id).isPresent();
        if (temp)
            throw new ValidationFailedException("");
        Category categoryDb = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Category"));
        categoryDb.setCategoryCode(request.getCode());
        categoryDb.setUpdatedBy(authUtils.getAdminUserFromAuthentication());
        categoryDb.setDescription(request.getDescription());
        categoryDb.setName(request.getName());
        try {
            var item = categoryRepository.save(categoryDb);
            return CategoryResponse.builder()
                    .code(item.getCategoryCode())
                    .description(item.getDescription())
                    .id(item.getId())
                    .name(item.getName())
                    .build();
        } catch (Exception ex) {
            throw new ActionFailedException(String.format("Failed update category with reason: %s", ex.getMessage()));
        }
    }

    @Override
    public CategoryResponse removeCategory(BigDecimal id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Category"));
        if (category.getProducts() == null || !category.getProducts().isEmpty())
            throw new ValidationFailedException("This category is in used, so please check again before remove it");
        category.setStatus(false);
        try {
            var item = categoryRepository.save(category);
            return CategoryResponse.builder()
                    .code(item.getCategoryCode())
                    .description(item.getDescription())
                    .id(item.getId())
                    .name(item.getName())
                    .build();
        } catch (Exception ex) {
            throw new ActionFailedException(
                    String.format("Failed to disable category with reason: %s", ex.getMessage()));
        }
    }

}
