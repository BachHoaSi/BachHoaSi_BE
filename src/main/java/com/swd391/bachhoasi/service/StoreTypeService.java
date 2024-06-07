package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.StoreTypeRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreLevelResponse;
import com.swd391.bachhoasi.model.dto.response.StoreTypeResponse;
import com.swd391.bachhoasi.model.entity.StoreType;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StoreTypeService {
    StoreTypeResponse createNewStoreType(StoreTypeRequest storeTypeRequest);
    void updateStoreType(BigDecimal id, String name, String description);
    Optional<StoreType> findById(BigDecimal id);
    PaginationResponse<StoreType> getStoreTypes(Pageable pagination, String keyword);
    StoreTypeResponse deleteStoreTypeById(BigDecimal id);
}
