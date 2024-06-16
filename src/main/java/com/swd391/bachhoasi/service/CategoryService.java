package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.response.CategoryResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;

public interface CategoryService {
    PaginationResponse<CategoryResponse> getAllCategory();
}
