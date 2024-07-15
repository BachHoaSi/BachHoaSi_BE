package com.swd391.bachhoasi.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreTypeRequest {
    @NotEmpty(message = "StoreType name should not be null or empty")
    private String name;
    @NotEmpty(message = "Description should not be null or empty")
    private String description;
    private Boolean status;
}
