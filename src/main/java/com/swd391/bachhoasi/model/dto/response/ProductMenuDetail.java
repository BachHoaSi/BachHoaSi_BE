package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ProductMenuDetail {
    private BigDecimal menuId;
    private BigDecimal basePrice;
    private Boolean status;
    private String adminName;
    private ProductResponse productDetails;
}
