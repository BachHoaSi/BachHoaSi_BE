package com.swd391.bachhoasi.model.dto.request;

import com.swd391.bachhoasi.model.constant.VehicleType;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class ShipperRequest {
    private String email;
    private String name;
    private String phone;
    private String licenseNumber;
    private Date licenseIssueDate;
    private String idCardNumber;
    private String idCardIssuePlace;
    private Date idCardIssueDate;
    private VehicleType vehicleType;

}
