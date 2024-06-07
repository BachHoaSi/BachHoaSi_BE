package com.swd391.bachhoasi.model.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StoreTypeResponse {
    private String name;
    private String description;
    private BigDecimal id;
}
