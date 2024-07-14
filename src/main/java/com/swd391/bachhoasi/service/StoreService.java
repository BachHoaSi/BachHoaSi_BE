package com.swd391.bachhoasi.service;

import java.math.BigDecimal;

import com.swd391.bachhoasi.model.dto.request.StoreRequest;
import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.constant.StoreStatus;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreResponseDto;

@Service
public interface StoreService {
    StoreResponseDto disableStore(BigDecimal id);
    StoreResponseDto updateStoreRegisterReview(BigDecimal id, StoreStatus status);
    PaginationResponse<StoreResponseDto> getAllStore(SearchRequestParamsDto request);
    StoreResponseDto updateStore(StoreRequest storeRequest);
}
