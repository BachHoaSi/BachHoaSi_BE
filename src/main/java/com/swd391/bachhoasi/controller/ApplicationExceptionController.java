package com.swd391.bachhoasi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.AuthFailedException;
import com.swd391.bachhoasi.model.exception.BachHoaSiBaseException;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.model.exception.UserNotFoundException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;

@RestControllerAdvice
public class ApplicationExceptionController {
    
    @ExceptionHandler({
        UserNotFoundException.class,
        AuthFailedException.class,
        NotFoundException.class,
        ActionFailedException.class,
        ValidationFailedException.class
    })
    public ResponseEntity<ResponseObject> bachHoaSiCustomException(BachHoaSiBaseException exception) {
        var responseError = exception.getErrorResponse();
        return ResponseEntity.status(responseError.status()).body(responseError);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseObject> accessDeniedException(AccessDeniedException exception) {
        var responseError = ResponseObject.builder()
        .isSuccess(false)
        .message(exception.getMessage())
        .code("AUTH_FAILED")
        .data(null)
        .status(HttpStatus.FORBIDDEN)
        .build();
        return ResponseEntity.status(responseError.status()).body(responseError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseObject> authenticationFailedException(AuthenticationException exception) {
        var responseError = ResponseObject.builder()
        .isSuccess(false)
        .message(exception.getMessage())
        .code("AUTH_FAILED")
        .data(null)
        .status(HttpStatus.UNAUTHORIZED)
        .build();
        return ResponseEntity.status(responseError.status()).body(responseError);
    }
}
