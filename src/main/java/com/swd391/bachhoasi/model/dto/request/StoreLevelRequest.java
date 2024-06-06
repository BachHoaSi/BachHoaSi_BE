package com.swd391.bachhoasi.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreLevelRequest {
    @NotBlank(message = "Description should't empty")
    private String description;
    @Min(value = 1, message = "Level must more than 1")
    private Integer level;
    @Min(value = 1, message = "From point must more than 1")
    private Double fromPoint;
    @Min(value = 1, message = "To point must more than 1")
    private Double toPoint;
}
