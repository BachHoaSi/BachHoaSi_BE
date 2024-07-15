package com.swd391.bachhoasi.controller;

import java.math.BigDecimal;

import com.swd391.bachhoasi.model.dto.request.AdminRequest;
import com.swd391.bachhoasi.model.dto.request.ShipperRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAnyRole('ADMIN')")
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


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getShipperOne(@PathVariable BigDecimal id) {
        var result = shipperService.getShipperDetail(id);
        return ResponseEntity.ok(
            ResponseObject.builder()
            .code("GET_SHIPPER_SUCCESS")
            .isSuccess(true)
            .message("Get Shipper Success")
            .data(result)
            .status(HttpStatus.OK)
            .build()
        );
    }

    @GetMapping("/shipper-least-order")
    public ResponseEntity<ResponseObject> getShipperLeastOrder() {
        var result = shipperService.getShipperWithLeastOrders();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("GET_SHIPPER_SUCCESS")
                        .isSuccess(true)
                        .message("Get Shipper Success")
                        .data(result)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @Operation(summary = "Rest Password", description = "Reset password by give shipper id, system will send new password to shipper email")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("reset-password")
    public ResponseEntity<ResponseObject> sendResetPassoword(@RequestParam BigDecimal id) throws MessagingException {
        var result = shipperService.resetPassword(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("RESET_PASSWORD_SUCCESS")
                        .isSuccess(true)
                        .data(result)
                        .message("Rest Password Success")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @Operation(summary = "Register new shipper", description = "Register new shipper, system will send password to shipper email")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ResponseObject> importNewAdmin(@RequestBody ShipperRequest shipperRequest) throws MessagingException {
        var result = shipperService.registerNewShipper(shipperRequest);
        var responseObject = ResponseObject
                .builder()
                .code("ADD_SHIPPER_SUCCESS")
                .message("Add shipper Successfully")
                .isSuccess(true)
                .data(result)
                .build();
        return ResponseEntity.ok(responseObject);
    }

    @Operation(summary = "Update shipper")
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ResponseObject> updateShipper (@PathVariable(name = "id") BigDecimal id, @RequestBody ShipperRequest request) {
        var result = shipperService.updateUser(id,request);
        var responseObject = ResponseObject
                .builder()
                .code("UPDATE_SHIPPER_SUCCESS")
                .message("Update Shipper Successfully")
                .isSuccess(true)
                .data(result)
                .build();
        return ResponseEntity.ok(responseObject);
    }
}
