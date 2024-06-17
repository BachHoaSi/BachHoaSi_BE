package com.swd391.bachhoasi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.ShipperService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shippers")
@Tag(name = "Shippers")
@RequiredArgsConstructor
public class ShipperController {
    private final ShipperService shipperService;

    @GetMapping
    public ResponseEntity<ResponseObject> getShippers (
        @PageableDefault(page = 0, size = 10, sort = "id") 
        Pageable pageable, 
        @RequestParam(name = "q") 
        String query){
        var queryDto = SearchRequestParamsDto.builder()
        .search(query)
        .pageable(pageable)
        .build();
        var result = shipperService.getAllShipper(queryDto);
        var responseObject = ResponseObject.builder()
                .code("SHIPPER_GET_SUCCESS")
                .message("Get shipper successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .data(result)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }
}
