package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.StoreTypeRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreLevelResponse;
import com.swd391.bachhoasi.model.dto.response.StoreTypeResponse;
import com.swd391.bachhoasi.model.entity.Store;
import com.swd391.bachhoasi.model.entity.StoreLevel;
import com.swd391.bachhoasi.model.entity.StoreType;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.repository.StoreTypeRepository;
import com.swd391.bachhoasi.service.StoreTypeService;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.swd391.bachhoasi.model.exception.ActionFailedException;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreTypeServiceImpl implements StoreTypeService {
    private final StoreTypeRepository storeTypeRepository;


    @Override
    public StoreTypeResponse createNewStoreType(StoreTypeRequest storeTypeRequest) {
        if(storeTypeRequest == null) throw new ValidationFailedException("Store type request is null, please check again !!!");
        StoreType storeTypeEntity = StoreType.builder()
                .name(storeTypeRequest.getName())
                .description(storeTypeRequest.getDescription())
                .status(storeTypeRequest.getStatus())
                .build();
        StoreType result = storeTypeRepository.save(storeTypeEntity);
        return mapToStoreTypeRespone(result);
    }

    @Override
    public StoreTypeResponse updateStoreType(StoreTypeRequest storeTypeRequest) {
        if (storeTypeRequest == null) {
            throw new ValidationFailedException("Store type id request is null, please check again !!!");
        }
        Optional<StoreType> storeTypeOptional = storeTypeRepository.findById(storeTypeRequest.getId());
        if (storeTypeOptional.isEmpty()) {
            throw new ValidationFailedException("Store type not found, please check again !!!");
        }

        StoreType storeTypeEntity = storeTypeOptional.get();
        storeTypeEntity.setName(storeTypeRequest.getName());
        storeTypeEntity.setDescription(storeTypeRequest.getDescription());
        storeTypeEntity.setStatus(storeTypeRequest.getStatus());

        try {
            StoreType updatedStoreType = storeTypeRepository.save(storeTypeEntity);
            return mapToStoreTypeRespone(updatedStoreType);
        } catch (Exception e) {
            throw new ValidationFailedException("Cannot update storeType, please check again !!!");
        }
    }

    @Override
    public Optional<StoreType> findById(BigDecimal id) {
        if (id == null)
            return Optional.empty();
        return storeTypeRepository.findById(id);
    }

    @Override
    public PaginationResponse<StoreType> getStoreTypes(Pageable pageable, String keyword) {
        Page<StoreTypeResponse> storeTypeResponsePage = storeTypeRepository.findByNameOrDescription(keyword, pageable)
                .map(item -> StoreTypeResponse.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .status(item.getStatus())
                        .build());

        return new PaginationResponse<>(convert(storeTypeResponsePage));

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
            return mapToStoreTypeRespone(deleteStoreType);
        } catch (Exception e) {
            throw new ValidationFailedException("Cannot update storeType, please check again !!!");
        }
    }

    public StoreTypeResponse mapToStoreTypeRespone(StoreType storeType) {
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
