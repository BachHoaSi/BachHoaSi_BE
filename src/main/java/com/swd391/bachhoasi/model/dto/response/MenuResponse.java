package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;
import java.sql.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class MenuResponse {
    @Schema(description = "Menu ID")
    private BigDecimal id;
    
    @Schema(description = "Date when menu was created")
    private Date createdDate;
    
    @Schema(description = "Date when menu was last updated")
    private Date updatedDate;
    
    @Schema(description = "Type of menu")
    private String type;
    
    @Schema(description = "Status of the menu")
    private Boolean status;
}
