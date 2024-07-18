package com.swd391.bachhoasi.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Validated
public class ProductMenuDTO {
    private BigDecimal id;
    private String productName;
    private Integer stockQuantity;
}
