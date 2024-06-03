package com.swd391.bachhoasi.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Validated
public class ProductRequest {

    @NotEmpty(message = "Product name should not be null or empty")
    private String name;
    @DecimalMin(value = "0.001", message = "Base price should not be less than 0.001")
    private BigDecimal basePrice;
    @NotEmpty(message = "Description should not be null or empty")
    private String description;
    @DecimalMin(value = "1", message = "Stock quantity should not be less than 1")
    private Integer stockQuantity;

    private String urlImages;
    private BigDecimal categoryId;

}
