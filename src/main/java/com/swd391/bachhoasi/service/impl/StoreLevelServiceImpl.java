package com.swd391.bachhoasi.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
import com.swd391.bachhoasi.util.TextUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreLevelServiceImpl implements StoreLevelService {
    private final StoreLevelRepository storeLevelRepository;
    public PaginationResponse<StoreLevelResponse> getStoreLevelList(Pageable pageable, Map<String, String> parameter) {
        if(parameter == null) parameter = new HashMap<>();
        var parameterList = TextUtils.convertKeysToCamel(parameter);
        try {
            Page<StoreLevelResponse> storeLevelPage = storeLevelRepository.searchAnyByParameter(parameterList, pageable)
            .map(item -> StoreLevelResponse.builder()
            .id(item.getId()).level(item.getLevel())
            .fromPoint(item.getFromPoint())
            .toPoint(item.getToPoint())
            .description(item.getDescription())
            .build());
            return new PaginationResponse<>(storeLevelPage);
        } catch (Exception ex ) {
            throw new ActionFailedException(ex.getMessage(), "STORE_LEVEL_GET_FAILED");
        }
    }

    public StoreLevelResponse createNewStoreLevel(StoreLevelRequest storeLevelRequest) {
        if(storeLevelRequest == null) throw new ValidationFailedException("Store level request is null, please check again !!!");
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
            throw new ValidationFailedException("Store level id isn't valid, please check again !!!");
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
