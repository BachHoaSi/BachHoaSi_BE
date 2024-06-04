package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreLevelResponse {
    private BigDecimal id;
    private String description;
    private Integer level;
    private Double fromPoint;
    private Double toPoint;
}
