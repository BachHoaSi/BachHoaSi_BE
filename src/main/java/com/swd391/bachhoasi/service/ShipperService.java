package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ShipperResponseDto;

public interface ShipperService {
    PaginationResponse<ShipperResponseDto> getAllShipper(SearchRequestParamsDto search);
}
