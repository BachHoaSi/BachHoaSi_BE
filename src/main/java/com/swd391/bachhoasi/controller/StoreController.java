package com.swd391.bachhoasi.controller;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.StoreService;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllStore(
        @PageableDefault(page = 0, size = 10)Pageable pageable,
        @RequestParam(name = "q", required = false) String query){
        var searchQuery = SearchRequestParamsDto.builder()
        .search(query)
        .pageable(pageable)
        .build();
        var result = storeService.getAllStore(searchQuery);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("GET_STORE_SUCCESS")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .message("Query Success")
            .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> disableStore(@PathVariable(value = "id") BigDecimal id) {
        var result = storeService.disableStore(id);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("DISABLE_STORE_SUCCESS")
            .data(result)
            .isSuccess(true)
            .status(HttpStatus.OK)
            .message("Disable Success")
            .build()
        );
    }
}
