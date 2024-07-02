package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminResponse {
    private BigDecimal id;
    private String username;
    @JsonProperty("full-name")
    private String fullName;
    private String role;
    @JsonProperty("is-active")
    private Boolean isActive;
    @JsonProperty("is-locked")
    private Boolean isLocked;
}
