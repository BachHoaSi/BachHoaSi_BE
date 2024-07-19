package com.swd391.bachhoasi.controller;

import com.swd391.bachhoasi.model.dto.request.ProductOrderDTO;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/work")
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> getTopSelling(
    ) {

        List<ProductOrderDTO> result = workService.getBestSellingProduct();
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
