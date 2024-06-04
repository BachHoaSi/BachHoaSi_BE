package com.swd391.bachhoasi.model.exception;

import org.springframework.http.HttpStatus;

import com.swd391.bachhoasi.model.dto.response.ResponseObject;

public class NotFoundException extends BachHoaSiBaseException {

    public NotFoundException(String message) {
        super(message);
        this.errorResponse = ResponseObject.builder()
        .code("NOT_FOUND")
        .message(message)
        .data(null)
        .isSuccess(false)
        .status(HttpStatus.OK)
        .build();
    }
    
}
