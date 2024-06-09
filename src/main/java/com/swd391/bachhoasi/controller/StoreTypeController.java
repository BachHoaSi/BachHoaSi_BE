package com.swd391.bachhoasi.controller;

import com.swd391.bachhoasi.model.dto.request.StoreLevelRequest;
import com.swd391.bachhoasi.model.dto.request.StoreTypeRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductResponse;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.model.dto.response.StoreTypeResponse;
import com.swd391.bachhoasi.model.entity.StoreType;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.service.StoreTypeService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/store-types")
@RequiredArgsConstructor
public class StoreTypeController {
    private final StoreTypeService storeTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> getAll(
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pagination,
            @RequestParam(required = false) String keyword
    ) {
        PaginationResponse<StoreType> storeTypes= storeTypeService.getStoreTypes(pagination, keyword);
        var responseObject = ResponseObject.builder()
                .code("STORE_TYPE_GET_SUCCESS")
                .message("Get store type successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .data(storeTypes)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> createNewStoreType(@RequestBody @Valid StoreTypeRequest storeType){
        var result = storeTypeService.createNewStoreType(storeType);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("STORE_CREATE_CREATE_SUCCESS")
                        .isSuccess(true)
                        .data(result)
                        .message("Create type level success")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> updateStoreType(@RequestBody @Valid StoreTypeRequest storeType
    ){

            storeTypeService.updateStoreType(storeType);
            return ResponseEntity.ok().build();

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteStoreType(@PathVariable(name = "id") BigDecimal id) {
        var result = storeTypeService.deleteStoreTypeById(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("STORE_TYPE_REMOVE_SUCCESS")
                        .isSuccess(true)
                        .status(HttpStatus.OK)
                        .data(result)
                        .message("Remove store type success")
                        .build()
        );
    }
}
