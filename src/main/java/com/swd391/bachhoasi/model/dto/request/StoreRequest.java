package com.swd391.bachhoasi.model.dto.request;

import com.swd391.bachhoasi.model.constant.StoreStatus;
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
public class StoreRequest {
    private BigDecimal id;
    private BigDecimal storeLevelId;
    private BigDecimal storeTypeId;
    private String name;
    private String zaloId;
    private String phoneNumber;
    private StoreStatus storeStatus;
}
