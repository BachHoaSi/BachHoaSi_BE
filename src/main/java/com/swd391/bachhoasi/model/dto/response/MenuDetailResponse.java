package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuDetailResponse {
    private BigDecimal id;
    private Integer level;
    private String type;
    private Boolean status;
    private List<ProductMenuDetail> productMenuDetails;
}
