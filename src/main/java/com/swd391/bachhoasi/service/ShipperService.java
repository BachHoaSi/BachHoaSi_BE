package com.swd391.bachhoasi.service;

import java.math.BigDecimal;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ShipperResponseDto;

public interface ShipperService {
    PaginationResponse<ShipperResponseDto> getAllShipper(SearchRequestParamsDto search);
    ShipperResponseDto getShipperDetail(BigDecimal id);
    ShipperResponseDto getShipperWithLeastOrders();

}
