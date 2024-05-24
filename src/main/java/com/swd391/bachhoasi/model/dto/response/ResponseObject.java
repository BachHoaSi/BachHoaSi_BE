package com.swd391.bachhoasi.model.dto.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;

@Builder
public record ResponseObject(
    Object data,
    String code,
    Boolean isSuccess,
    HttpStatus status,
    String message
) {
    
}
