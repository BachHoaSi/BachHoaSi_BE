package com.swd391.bachhoasi.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreResponseDto;

@Service
public interface StoreService {
    StoreResponseDto disableStore(BigDecimal id);
    PaginationResponse<StoreResponseDto> getAllStore(SearchRequestParamsDto request);
}
