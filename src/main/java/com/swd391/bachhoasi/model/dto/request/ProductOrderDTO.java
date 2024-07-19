package com.swd391.bachhoasi.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductOrderDTO {
    private BigDecimal id;
    private String productName;
    private Integer quantity;
}
