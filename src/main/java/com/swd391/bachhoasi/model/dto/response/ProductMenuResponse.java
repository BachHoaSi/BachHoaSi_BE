package com.swd391.bachhoasi.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductMenuResponse {
    private BigDecimal id;
    private BigDecimal productId;
    private BigDecimal menuId;
    private BigDecimal basePrice;
    private Boolean status;
    private String adminName;
    private ProductResponse productDetails;
}
