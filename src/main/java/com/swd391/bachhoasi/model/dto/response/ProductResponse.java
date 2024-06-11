package com.swd391.bachhoasi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private BigDecimal id;
    @JsonProperty("product-code")
    private String productCode;
    private String name;
    @JsonProperty("base-price")
    private BigDecimal basePrice;
    @JsonProperty("url-image")
    private String urlImages;
    private String description;
    @JsonProperty("stock-quantity")
    private Integer stockQuantity;
    @JsonProperty("category-name")
    private String categoryName;
    private Boolean status;
    @JsonProperty("last-import-date")
    private Date lastImportDate;
    @JsonProperty("created-date")
    private Date createdDate;

}
