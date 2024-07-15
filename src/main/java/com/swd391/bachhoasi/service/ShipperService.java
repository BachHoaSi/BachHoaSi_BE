package com.swd391.bachhoasi.service;

import java.math.BigDecimal;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.request.ShipperRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ShipperResponseDto;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;

public interface ShipperService {
    JavaMailSender getJavaMailSender();
    PaginationResponse<ShipperResponseDto> getAllShipper(SearchRequestParamsDto search);
    ShipperResponseDto getShipperDetail(BigDecimal id);
    ShipperResponseDto getShipperWithLeastOrders();
    ShipperResponseDto resetPassword(BigDecimal id) throws MessagingException;
    ShipperResponseDto registerNewShipper(ShipperRequest shipperRequest) throws MessagingException;
}
