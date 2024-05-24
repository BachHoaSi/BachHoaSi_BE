package com.swd391.bachhoasi.controller;

import com.swd391.bachhoasi.model.dto.request.LoginDto;
import com.swd391.bachhoasi.model.dto.response.LoginResponse;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authentication")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginDto loginDto){
        LoginResponse jwtAuthResponse = authService.login(loginDto);
        var responseObject = ResponseObject.builder()
            .code("AUTH_SUCCESS")
            .message("Welcome To Bach Hoa Si")
            .status(HttpStatus.OK)
            .isSuccess(true)
            .data(jwtAuthResponse)
            .build();
        return ResponseEntity.ok()
            .headers(jwtAuthResponse.getAuthenticationHeader())
            .body(responseObject);
    }

}
