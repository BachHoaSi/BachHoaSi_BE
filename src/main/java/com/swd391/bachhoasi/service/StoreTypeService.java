package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.StoreTypeRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreTypeBasicResponse;
import com.swd391.bachhoasi.model.dto.response.StoreTypeResponse;
import com.swd391.bachhoasi.model.entity.StoreType;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface StoreTypeService {
    StoreTypeResponse createNewStoreType(StoreTypeRequest storeTypeRequest);
    PaginationResponse<StoreTypeBasicResponse> getBasicAllStoreType();
    StoreTypeResponse updateStoreType(StoreTypeRequest storeTypeRequest);
    Optional<StoreType> findById(BigDecimal id);
    public PaginationResponse<StoreTypeResponse> getStoreTypes(Pageable pageable, Map<String, String> parameter);
    StoreTypeResponse deleteStoreTypeById(BigDecimal id);
}
