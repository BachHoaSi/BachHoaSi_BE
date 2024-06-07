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
        try{
            StoreTypeResponse storeTypeResponse = storeTypeService.createNewStoreType(storeType);
            var responseObject = ResponseObject.builder()
                    .data(storeTypeResponse)
                    .code("STORE_TYPE_ADD_SUCCESS")
                    .message("Add store type successfully")
                    .status(HttpStatus.OK)
                    .isSuccess(true)
                    .build();
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            var responseObject = ResponseObject.builder()
                    .code("STORE_TYPE_ADD_FAILED")
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .isSuccess(false)
                    .build();
            return ResponseEntity.ok().body(responseObject);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> updateStoreType(@RequestParam BigDecimal id,
                                                          @RequestParam String name,
                                                          @RequestParam String description){
        try {
            storeTypeService.updateStoreType(id, name, description);
            return ResponseEntity.ok().build();
        } catch (ValidationFailedException e) {
            return ResponseEntity.badRequest().body(null);
        }
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
