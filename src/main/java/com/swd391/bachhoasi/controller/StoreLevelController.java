package com.swd391.bachhoasi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.StoreLevelService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/store-level")
@RequiredArgsConstructor
public class StoreLevelController {

    private final StoreLevelService storeLevelService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> getAll(
        @PageableDefault(page = 0, size = 20, sort = "id", direction = Direction.DESC) Pageable pagination,
        @RequestParam(name = "getAll", required = false, defaultValue = "false") Boolean getAll
    ) {
        var result = storeLevelService.getStoreLevelList(pagination, getAll);
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
    
}
