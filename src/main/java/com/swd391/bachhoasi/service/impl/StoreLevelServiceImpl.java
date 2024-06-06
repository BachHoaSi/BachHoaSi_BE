package com.swd391.bachhoasi.service.impl;

import java.math.BigDecimal;
import java.util.Collection;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.dto.request.StoreLevelRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreLevelResponse;
import com.swd391.bachhoasi.model.entity.StoreLevel;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.repository.StoreLevelRepository;
import com.swd391.bachhoasi.service.StoreLevelService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreLevelServiceImpl implements StoreLevelService {
    private final StoreLevelRepository storeLevelRepository;

    public PaginationResponse<StoreLevelResponse> getStoreLevelList(Pageable pageable, boolean getAll) {
        if (getAll) {
            Collection<StoreLevelResponse> collection = storeLevelRepository.findAll().stream()
                    .map(item -> StoreLevelResponse.builder()
                            .id(item.getId())
                            .level(item.getLevel())
                            .fromPoint(item.getFromPoint())
                            .toPoint(item.getToPoint())
                            .build())
                    .toList();
            return new PaginationResponse<>(collection);
        } else {
            Page<StoreLevelResponse> storeLevelPage = storeLevelRepository.findAll(pageable)
                    .map(item -> StoreLevelResponse.builder()
                            .id(item.getId())
                            .level(item.getLevel())
                            .fromPoint(item.getFromPoint())
                            .toPoint(item.getToPoint())
                            .build());
            return new PaginationResponse<>(storeLevelPage);
        }
    }

    public StoreLevelResponse createNewStoreLevel(StoreLevelRequest storeLevelRequest) {
        if(storeLevelRequest == null) throw new ValidationFailedException("");
        var storeLevelEntity = StoreLevel.builder()
        .description(storeLevelRequest.getDescription())
        .level(storeLevelRequest.getLevel())
        .fromPoint(storeLevelRequest.getFromPoint())
        .toPoint(storeLevelRequest.getToPoint())
        .build();
        try {
            storeLevelRepository.save(storeLevelEntity);
            return StoreLevelResponse.builder()
            .id(storeLevelEntity.getId())
            .level(storeLevelEntity.getLevel())
            .fromPoint(storeLevelEntity.getFromPoint())
            .toPoint(storeLevelEntity.getToPoint())
            .description(storeLevelEntity.getDescription())
            .build();
        }catch(ConstraintViolationException cve){
            throw new ActionFailedException(cve.getMessage(), "STORE_LEVEL_LEVEL_DUPLICATE");
        }catch(Exception ex) {
            throw new ActionFailedException(ex.getMessage(), "SAVE_STORE_LEVEL_FAILED");
        }
    }

    public StoreLevelResponse removeStoreLevelById(BigDecimal id) {
        if(id.intValue() < 0) 
            throw new ValidationFailedException("");
        StoreLevel deletedObject = storeLevelRepository.findById(id).orElseThrow(
            () -> new NotFoundException(String.format("Not found store level with id: %s", id.toString()))
        );
        try {
            storeLevelRepository.delete(deletedObject);
        }catch(Exception ex) {
            throw new ActionFailedException("This store level is used by others, please remove them before this");
        }
        
        return StoreLevelResponse.builder()
            .id(deletedObject.getId())
            .level(deletedObject.getLevel())
            .fromPoint(deletedObject.getFromPoint())
            .toPoint(deletedObject.getToPoint())
            .description(deletedObject.getDescription())
            .build();
    }
}
