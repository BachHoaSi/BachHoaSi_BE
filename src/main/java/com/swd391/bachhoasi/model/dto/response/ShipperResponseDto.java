package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swd391.bachhoasi.model.constant.ShipperStatus;
import com.swd391.bachhoasi.model.constant.ShippingStatus;
import com.swd391.bachhoasi.model.constant.VehicleType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipperResponseDto {
    private BigDecimal id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("status")
    private ShipperStatus status;
    @JsonProperty("shipping-status")
    private ShippingStatus shippingStatus;
    @JsonProperty("license-number")
    private String licenseNumber;
    @JsonProperty("license-issue-date")
    private Date licenseIssueDate;
    @JsonProperty("id-card-number")
    private String idCardNumber;
    @JsonProperty("id-card-issue-place")
    private String idCardIssuePlace;
    @JsonProperty("id-card-issue-date")
    private Date idCardIssueDate;
    @JsonProperty("vehicle-type")
    private VehicleType vehicleType;
    @JsonProperty("created-date")
    private Date createdDate;
    @JsonProperty("updated-date")
    private Date updatedDate;
    @JsonProperty("is-active")
    private Boolean isActive;
    @JsonProperty("is-locked")
    private Boolean isLocked;
}
