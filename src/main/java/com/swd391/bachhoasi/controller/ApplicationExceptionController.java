package com.swd391.bachhoasi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.model.exception.AuthFailedException;
import com.swd391.bachhoasi.model.exception.UserNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionController {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseObject> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.ok().body(
            exception.getErrorResponse()
        );
    }
    @ExceptionHandler(AuthFailedException.class)
    public ResponseEntity<ResponseObject> authFailedException(AuthFailedException exception) {
        return ResponseEntity.ok().body(
            exception.getErrorResponse()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseObject> authFailedException(AccessDeniedException exception) {
        var responseError = ResponseObject.builder()
        .isSuccess(false)
        .message(exception.getMessage())
        .code("AUTH_FAILED")
        .data(null)
        .status(HttpStatus.FORBIDDEN)
        .build();
        return ResponseEntity.status(responseError.status()).body(responseError);
    }
}
