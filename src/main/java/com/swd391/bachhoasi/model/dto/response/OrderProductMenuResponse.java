package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class OrderProductMenuResponse {
    private BigDecimal id;
    private Integer quantity;
    private String productName;
    private String url;
    private BigDecimal price;
    private String category;
}
