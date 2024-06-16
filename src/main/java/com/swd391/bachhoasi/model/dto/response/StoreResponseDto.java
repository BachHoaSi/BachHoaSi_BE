package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class StoreResponseDto {
    private BigDecimal id;
    private String name;
    private String type;
    private Integer point;
    private Boolean status;
    private String location;
    @JsonProperty("store-level")
    private Integer storeLevel;
}
