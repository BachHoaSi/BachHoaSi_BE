package com.swd391.bachhoasi.service.impl;

import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ShipperResponseDto;
import com.swd391.bachhoasi.repository.ShipperRepository;
import com.swd391.bachhoasi.service.ShipperService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepository shipperRepository;

    @Override
    public PaginationResponse<ShipperResponseDto> getAllShipper(SearchRequestParamsDto search) {
        var data = shipperRepository.searchAnyByParameter(search.search(), search.pagination())
        .map(item -> ShipperResponseDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .phone(item.getPhone())
                        .email(item.getEmail())
                        .status(item.getStatus())
                        .shippingStatus(item.getShippingStatus())
                        .licenseNumber(item.getLicenseNumber())
                        .licenseIssueDate(item.getLicenseIssueDate())
                        .idCardNumber(item.getIdCardNumber())
                        .idCardIssuePlace(item.getIdCardIssuePlace())
                        .idCardIssueDate(item.getIdCardIssueDate())
                        .vehicleType(item.getVehicleType())
                        .createdDate(item.getCreatedDate())
                        .updatedDate(item.getUpdatedDate())
                        .isActive(item.getIsActive())
                        .isLocked(item.getIsLocked())
                        .build());
        return new PaginationResponse<>(data);
    }

}
