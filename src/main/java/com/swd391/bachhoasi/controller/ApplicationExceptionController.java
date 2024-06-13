package com.swd391.bachhoasi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.AuthFailedException;
import com.swd391.bachhoasi.model.exception.BachHoaSiBaseException;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.model.exception.UserNotFoundException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;

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
    @ExceptionHandler({
        InvalidKeyException.class,
        ExpiredJwtException.class,
        UnsupportedJwtException.class,
        SignatureException.class,
    })
    public ResponseEntity<ResponseObject> tokenFailedException(JwtException exception) {
        var responseError = ResponseObject.builder()
        .isSuccess(false)
        .message(exception.getMessage())
        .code("AUTH_FAILED")
        .data(null)
        .status(HttpStatus.UNAUTHORIZED)
        .build();
        return ResponseEntity.status(responseError.status()).body(responseError);
    }
    @ExceptionHandler({
        UsernameNotFoundException.class,
        BadCredentialsException.class,
        LockedException.class,
        DisabledException.class,
        AccountStatusException.class,
        InsufficientAuthenticationException.class
    })
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject> somethingWrongException(Exception ex) {
        var responseError = ResponseObject.builder()
        .isSuccess(false)
        .message(ex.getMessage())
        .code("SOMETHING_WRONG")
        .data(null)
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .build();
        return ResponseEntity.status(responseError.status()).body(responseError);
    }
}
