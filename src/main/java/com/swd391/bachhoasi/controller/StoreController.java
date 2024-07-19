package com.swd391.bachhoasi.controller;

import com.swd391.bachhoasi.model.dto.request.StoreRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.request.StoreReviewRequest;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.StoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllStore(
        @Schema
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
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
    @Operation(summary = "Update store review status", description = "Updates the review status of a store")
    @PatchMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ResponseObject> updateStoreReviewStatus(@Schema @RequestBody StoreReviewRequest request) {
        var result = storeService.updateStoreRegisterReview(request.id(), request.status());
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ResponseObject> updateStore(@RequestBody @Valid StoreRequest storeRequest) {
        var result = storeService.updateStore(storeRequest);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("STORE_UPDATE_SUCCESS")
                        .isSuccess(true)
                        .status(HttpStatus.OK)
                        .data(result)
                        .message("Update store success")
                        .build()
        );
    }

    @Operation(summary = "Active", description = "Updates store status to Accepted")
    @PatchMapping("active/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ResponseObject> activeStore(@RequestParam BigDecimal id) {
        var result = storeService.activateStore(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("ACTIVATE_STORE_SUCCESS")
                        .data(result)
                        .isSuccess(true)
                        .status(HttpStatus.OK)
                        .message("Activate Success")
                        .build()
        );
    }
}
