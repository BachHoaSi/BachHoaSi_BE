package com.swd391.bachhoasi.service.impl;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreLevelResponse;
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
}
