package com.swd391.bachhoasi.service.impl;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.constant.StoreStatus;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreResponseDto;
import com.swd391.bachhoasi.model.entity.Store;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.repository.StoreRepository;
import com.swd391.bachhoasi.service.StoreService;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;

    @Override
    public PaginationResponse<StoreResponseDto> getAllStore(SearchRequestParamsDto request) {
        var result = storeRepository.searchAnyByParameter(request.search(), request.pagination()).map(item -> {
            Integer storeLevel = item.getStoreLevel() == null ? 0 : item.getStoreLevel().getLevel();
            String type = item.getType() == null ? "" : item.getType().getName();
            return StoreResponseDto.builder()
            .id(item.getId())
            .name(item.getName())
            .type(type)
            .point(item.getPoint())
            .status(item.getStatus())
            .location(item.getLocation())
            .storeLevel(storeLevel)
            .build();
        });
        return new PaginationResponse<>(result);
    }

    public StoreResponseDto updateStoreRegisterReview(BigDecimal id, StoreStatus status) {
        Store item = storeRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Not found store with id: %s", id.toString())));
        if(!item.getStatus().booleanValue() || item.getCreationStatus() == StoreStatus.ACCEPTED) throw new ValidationFailedException("Can't update a store review status with a disable store or that store is accepts");
        item.setCreationStatus(status);
        storeRepository.save(item);
        return StoreResponseDto.builder()
            .id(item.getId())
            .name(item.getName())
            .type(item.getType() == null ? "" : item.getType().getName())
            .point(item.getPoint())
            .status(item.getStatus())
            .location(item.getLocation())
            .storeLevel(item.getStoreLevel() == null ? 0 : item.getStoreLevel().getLevel())
            .build();
    }
    public StoreResponseDto disableStore(BigDecimal id) {
        Store item = storeRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Not found store with id: %s", id.toString())));
        item.setStatus(false);
        storeRepository.save(item);
        return StoreResponseDto.builder()
            .id(item.getId())
            .name(item.getName())
            .type(item.getType() == null ? "" : item.getType().getName())
            .point(item.getPoint())
            .status(item.getStatus())
            .location(item.getLocation())
            .storeLevel(item.getStoreLevel() == null ? 0 : item.getStoreLevel().getLevel())
            .build();
    }
}
