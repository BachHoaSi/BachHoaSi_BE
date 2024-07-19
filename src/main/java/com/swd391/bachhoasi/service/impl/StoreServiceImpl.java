package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.StoreRequest;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.repository.StoreLevelRepository;
import com.swd391.bachhoasi.repository.StoreTypeRepository;
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
    private final StoreLevelRepository storeLevelRepository;
    private final StoreTypeRepository storeTypeRepository;

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
            .storeStatus(item.getCreationStatus())
            .storeLevel(storeLevel)
            .build();
        });
        return new PaginationResponse<>(result);
    }

    @Override
    public StoreResponseDto updateStore(StoreRequest storeRequest) {
        if (storeRequest == null)
            throw new ValidationFailedException("Store Request is null, please provide a valid request");
        var storeLevel = storeLevelRepository.findById(storeRequest.getStoreLevelId());
        var storeType = storeTypeRepository.findById(storeRequest.getStoreTypeId());
        if (storeLevel.isEmpty() || storeType.isEmpty())
            throw new ValidationFailedException("Store level or type is null, please provide a valid request");
        try {
            var store = storeRepository.findById(storeRequest.getStoreTypeId()).get();
            store.setName(storeRequest.getName());
            store.setStoreLevel(storeLevel.get());
            store.setType(storeType.get());
            store.setPhoneNumber(storeRequest.getPhoneNumber());
            store.setStatus(store.getStatus());

            Store updatedStore = storeRepository.save(store);
            return StoreResponseDto.builder()
                    .name(updatedStore.getName())
                    .storeLevel(updatedStore.getStoreLevel().getLevel())
                    .type(updatedStore.getType().getName())
                    .status(updatedStore.getStatus())
                    .build();
        }catch (Exception e) {
            throw new ActionFailedException("Cannot update store, please check again !!!");
        }
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
