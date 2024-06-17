package com.swd391.bachhoasi.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {
    private String name;
    private String code;
    private String description;
}
