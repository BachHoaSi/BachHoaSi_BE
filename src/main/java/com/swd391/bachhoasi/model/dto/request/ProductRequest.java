package com.swd391.bachhoasi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private BigDecimal id;
    @NotEmpty(message = "Product name should not be null or empty")
    private String name;
    @DecimalMin(value = "0.001", message = "Base price should not be less than 0.001")
    @JsonProperty("base-price")
    private BigDecimal basePrice;
    @NotEmpty(message = "Description should not be null or empty")
    private String description;
    @DecimalMin(value = "1", message = "Stock quantity should not be less than 1")
    @JsonProperty("stock-quantity")
    private Integer stockQuantity;
    @JsonProperty("url-images")
    private String urlImages;
    @JsonProperty("category-id")
    private BigDecimal categoryId;
}
