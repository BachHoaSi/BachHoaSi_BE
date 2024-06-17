package com.swd391.bachhoasi.model.dto.request;

import java.math.BigDecimal;

import com.swd391.bachhoasi.model.constant.StoreStatus;

import io.swagger.v3.oas.annotations.Parameter;

public record StoreReviewRequest(
@Parameter(description = "Store ID", required = true)    
BigDecimal id, 
@Parameter(description = "Store status (enum: REJECTED, ACCEPTED, PENDING, CREATED)", required = true)
StoreStatus status) {
    
}
