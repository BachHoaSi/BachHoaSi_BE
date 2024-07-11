package com.swd391.bachhoasi.model.dto.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.swd391.bachhoasi.model.entity.ProductMenuId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductMenuRequest {
    @JsonProperty("product-id")
    private BigDecimal productId;
    private BigDecimal menuId;
    private BigDecimal price;
    private Boolean status;
}
