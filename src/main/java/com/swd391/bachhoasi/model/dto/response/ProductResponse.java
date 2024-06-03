package com.swd391.bachhoasi.model.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private String productCode;
    private String name;
    private BigDecimal basePrice;
    private String urlImages;
    private String description;
    private Integer stockQuantity;
    private String categoryName;
}
