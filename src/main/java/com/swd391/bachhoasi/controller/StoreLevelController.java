package com.swd391.bachhoasi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.request.StoreLevelRequest;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.StoreLevelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/store-levels")
@RequiredArgsConstructor
public class StoreLevelController {

    private final StoreLevelService storeLevelService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> getAll(
        @PageableDefault(page = 0, size = 20, sort = "id", direction = Direction.DESC) Pageable pagination,
        @RequestParam(required = false, name = "q") String query
    ) {
        var queryDto = SearchRequestParamsDto.builder()
            .search(query)
            .wrapSort(pagination)
            .build();
        var result = storeLevelService.getStoreLevelList(queryDto.pagination(),queryDto.search());
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("STORE_LEVEL_GET_SUCCESS")
            .isSuccess(true)
            .data(result)
            .message("Get Store Level Success")
            .status(HttpStatus.OK)
            .build()
        );
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> createStoreLevel(@RequestBody @Valid StoreLevelRequest storeLevel) {
        var result = storeLevelService.createNewStoreLevel(storeLevel);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("STORE_LEVEL_CREATE_SUCCESS")
            .isSuccess(true)
            .data(result)
            .message("Create store level success")
            .status(HttpStatus.OK)
            .build()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> removeStoreLevel(@PathVariable(name = "id") BigDecimal id) {
        var result = storeLevelService.removeStoreLevelById(id);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("STORE_LEVEL_REMOVE_SUCCESS")
            .isSuccess(true)
            .status(HttpStatus.OK)
            .data(result)
            .message("Remove store level success")
            .build()
        );
    }
}
