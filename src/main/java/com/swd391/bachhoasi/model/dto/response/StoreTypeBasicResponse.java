package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreTypeBasicResponse {
    private BigDecimal id;
    private String name;
}
