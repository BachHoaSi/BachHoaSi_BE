package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.StoreTypeRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreLevelResponse;
import com.swd391.bachhoasi.model.dto.response.StoreTypeResponse;
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
                .build();
        StoreType result = storeTypeRepository.save(storeTypeEntity);
        return mapToStoreTypeRespone(result);
    }

    @Override
    public void updateStoreType(BigDecimal id, String name, String description) {
        if (id == null) {
            throw new ValidationFailedException("Store type id request is null, please check again !!!");
        }
        try {
            StoreType storeTypeEntity = storeTypeRepository.findById(id).orElseThrow(() ->
                    new ValidationFailedException("Store type not found, please check again !!!"));
            storeTypeEntity.setName(name);
            storeTypeEntity.setDescription(description);

            storeTypeRepository.save(storeTypeEntity);
        }catch (Exception e) {
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
        if (keyword == null) {
            Page<StoreType > p1 =  storeTypeRepository.findAll(pageable);
            return new PaginationResponse<>(p1);
        } else  {
            Page<StoreType> p2 = storeTypeRepository.findByNameOrDescription(keyword, pageable)
                    .map(item -> StoreType.builder().name(item.getName()).description(item.getDescription()).build());
            return new PaginationResponse<>(p2);
        }
    }

    @Override
    public StoreTypeResponse deleteStoreTypeById(BigDecimal id) {
        if(id.intValue() < 0)
            throw new ValidationFailedException("Store type id isn't valid, please check again !!!");
        StoreType deletedObject = storeTypeRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Not found store type with id: %s", id.toString()))
        );
        try {
            storeTypeRepository.delete(deletedObject);
        }catch(Exception ex) {
            throw new ActionFailedException("This store type is used by others, please remove them before this");
        }

        return StoreTypeResponse.builder()
                .id(deletedObject.getId())
                .name(deletedObject.getName())
                .description(deletedObject.getDescription())
                .build();
    }

    public StoreTypeResponse mapToStoreTypeRespone(StoreType storeType) {
        return StoreTypeResponse.builder()
                .name(storeType.getName())
                .description(storeType.getDescription())
                .build();
    }

}
