package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.request.StoreTypeRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreTypeBasicResponse;
import com.swd391.bachhoasi.model.dto.response.StoreTypeResponse;
import com.swd391.bachhoasi.model.entity.StoreType;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.repository.StoreTypeRepository;
import com.swd391.bachhoasi.service.StoreTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreTypeServiceImpl implements StoreTypeService {
    private final StoreTypeRepository storeTypeRepository;

    @Override
    public StoreTypeResponse createNewStoreType(StoreTypeRequest storeTypeRequest) {
        if (storeTypeRequest == null)
            throw new ValidationFailedException("Store type request is null, please check again !!!");
        StoreType storeTypeEntity = StoreType.builder()
                .name(storeTypeRequest.getName())
                .description(storeTypeRequest.getDescription())
                .status(storeTypeRequest.getStatus())
                .build();
        StoreType result = storeTypeRepository.save(storeTypeEntity);
        return mapToStoreTypeResponse(result);
    }

    @Override
    public StoreTypeResponse updateStoreType(BigDecimal id, StoreTypeRequest storeTypeRequest) {
        if (storeTypeRequest == null) {
            throw new ValidationFailedException("Store type id request is null, please check again !!!");
        }
        Optional<StoreType> storeTypeOptional = storeTypeRepository.findById(id);
        if (storeTypeOptional.isEmpty()) {
            throw new ValidationFailedException("Store type not found, please check again !!!");
        }

        StoreType storeTypeEntity = storeTypeOptional.get();
        storeTypeEntity.setName(storeTypeRequest.getName());
        storeTypeEntity.setDescription(storeTypeRequest.getDescription());
        storeTypeEntity.setStatus(storeTypeRequest.getStatus());

        try {
            StoreType updatedStoreType = storeTypeRepository.save(storeTypeEntity);
            return mapToStoreTypeResponse(updatedStoreType);
        } catch (Exception e) {
            throw new ActionFailedException("Cannot update storeType, please check again !!!");
        }
    }

    @Override
    public Optional<StoreType> findById(BigDecimal id) {
        if (id == null)
            return Optional.empty();
        return storeTypeRepository.findById(id);
    }

    @Override
    public PaginationResponse<StoreTypeResponse> getStoreTypes(SearchRequestParamsDto request) {
        try {
            Page<StoreTypeResponse> storeLevelPage = storeTypeRepository.searchAnyByParameter(request.search(), request.pagination())
                    .map(item -> StoreTypeResponse.builder()
                            .id(item.getId())
                            .name(item.getName())
                            .description(item.getDescription())
                            .status(item.getStatus())
                            .build());
            return new PaginationResponse<>(storeLevelPage);
        } catch (Exception ex ) {
            throw new ActionFailedException(ex.getMessage(), "STORE_TYPE_GET_FAILED");
        }
    }

    @Override
    public PaginationResponse<StoreTypeBasicResponse> getBasicAllStoreType() {
        var result = storeTypeRepository.findAll().stream().map(item -> StoreTypeBasicResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .build()).toList();
        return new PaginationResponse<>(result);
    }

    @Override
    public StoreTypeResponse deleteStoreTypeById(BigDecimal id) {
        if (id == null) {
            throw new ValidationFailedException("Store type id request is null, please check again !!!");
        }
        Optional<StoreType> storeTypeOptional = storeTypeRepository.findById(id);
        if (storeTypeOptional.isEmpty()) {
            throw new ValidationFailedException("Store type not found, please check again !!!");
        }

        StoreType storeTypeEntity = storeTypeOptional.get();
        storeTypeEntity.setStatus(false);

        try {
            StoreType deleteStoreType = storeTypeRepository.save(storeTypeEntity);
            return mapToStoreTypeResponse(deleteStoreType);
        } catch (Exception e) {
            throw new ValidationFailedException("Cannot update storeType, please check again !!!");
        }
    }

    public StoreTypeResponse mapToStoreTypeResponse(StoreType storeType) {
        return StoreTypeResponse.builder()
                .name(storeType.getName())
                .description(storeType.getDescription())
                .build();
    }

    public Page<StoreType> convert(Page<StoreTypeResponse> response) {
        return response.map(storeTypeResponse -> {
            StoreType storeType = new StoreType();
            storeType.setId(storeTypeResponse.getId());
            storeType.setName(storeTypeResponse.getName());
            storeType.setDescription(storeTypeResponse.getDescription());
            storeType.setStatus(storeTypeResponse.getStatus());
            return storeType;
        });
    }
}