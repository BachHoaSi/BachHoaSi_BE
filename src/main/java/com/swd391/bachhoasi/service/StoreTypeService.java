package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.request.StoreTypeRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreTypeBasicResponse;
import com.swd391.bachhoasi.model.dto.response.StoreTypeResponse;
import com.swd391.bachhoasi.model.entity.StoreType;
import java.math.BigDecimal;
import java.util.Optional;

public interface StoreTypeService {
    StoreTypeResponse createNewStoreType(StoreTypeRequest storeTypeRequest);
    PaginationResponse<StoreTypeBasicResponse> getBasicAllStoreType();
    StoreTypeResponse updateStoreType(BigDecimal id, StoreTypeRequest storeTypeRequest);
    Optional<StoreType> findById(BigDecimal id);
    PaginationResponse<StoreTypeResponse> getStoreTypes(SearchRequestParamsDto request);
    StoreTypeResponse deleteStoreTypeById(BigDecimal id);
}
