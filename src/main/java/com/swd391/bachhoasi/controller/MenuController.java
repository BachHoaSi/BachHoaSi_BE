package com.swd391.bachhoasi.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
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
    @PostMapping("/{id}")
    public ResponseEntity<ResponseObject> addProductToMenu (ProductMenuRequest request, @PathVariable(name = "id") BigDecimal id) {
        var result = menuService.addProductToMenu(id, request);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("ADD_PRODUCT_MENU_SUCCESS")
            .message("Add Product Menu Success")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .build()
        );
    }
    @GetMapping
    @Operation(summary = "Get all menus")
    public ResponseEntity<ResponseObject> getAllMenu(@RequestParam(name = "q") String q, @PageableDefault(page = 0, size = 20, sort = "id") Pageable page) {
        var queryDto = SearchRequestParamsDto.builder()
        .search(q)
        .wrapSort(page)
        .build();
        var result = menuService.getAllMenu(queryDto);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("GET_MENU_SUCCESS")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .message("Query Success")
            .build()
        );
    }
}