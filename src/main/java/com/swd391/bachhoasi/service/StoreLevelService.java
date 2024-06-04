package com.swd391.bachhoasi.service;

import org.springframework.data.domain.Pageable;

import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreLevelResponse;

public interface StoreLevelService {
    PaginationResponse<StoreLevelResponse> getStoreLevelList(Pageable pageable, boolean getAll);
}
