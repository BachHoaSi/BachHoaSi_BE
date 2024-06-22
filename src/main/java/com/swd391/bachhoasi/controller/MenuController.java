package com.swd391.bachhoasi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    @PostMapping("/local")
    public ResponseEntity<ResponseObject> storeInitMenu () {
        var result = menuService.initStoreMenu();
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("INIT_MENU_SUCCESS")
            .message("Init Menu By Hand Success")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .build()
        );
    }
}
