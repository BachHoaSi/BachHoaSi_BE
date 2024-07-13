package com.swd391.bachhoasi.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swd391.bachhoasi.model.dto.request.AdminRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.AdminResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.AdminService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("admins")
public class AdminController {
    private final AdminService adminService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ResponseObject> getAllAdmin(
        @RequestParam(required = false, name = "q") String query, 
        @PageableDefault(page = 0, size = 20, sort = "id") Pageable page){
            var queryDto = SearchRequestParamsDto.builder()
            .search(query)
            .wrapSort(page)
            .build();
        PaginationResponse<AdminResponse> admins = adminService.getAllAdmin(queryDto);
        var responseObject = ResponseObject
            .builder()
            .code("GET_ADMIN_SUCCESS")
            .message("Get Admin List Successfully")
            .isSuccess(true)
            .data(admins)
            .build();
        return ResponseEntity.ok(responseObject);
    }
    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> getAdminById(@PathVariable(name = "id") BigDecimal id) {
        var result = adminService.getAdminById(id);
        var responseObject = ResponseObject
            .builder()
            .code("GET_ADMIN_SUCCESS")
            .message("Get Admin Successfully")
            .isSuccess(true)
            .data(result)
            .build();
        return ResponseEntity.ok(responseObject);
    }
    @PostMapping
    public ResponseEntity<ResponseObject> importNewAdmin(@RequestBody AdminRequest adminRequest) {
        var result = adminService.importNewUser(adminRequest);
        var responseObject = ResponseObject
            .builder()
            .code("ADD_ADMIN_SUCCESS")
            .message("Add Admin Successfully")
            .isSuccess(true)
            .data(result)
            .build();
        return ResponseEntity.ok(responseObject);
    }
    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> updateAdmin (@PathVariable(name = "id") BigDecimal id, @RequestBody AdminRequest request) {
        var result = adminService.updateUser(id,request);
        var responseObject = ResponseObject
            .builder()
            .code("UPDATE_ADMIN_SUCCESS")
            .message("Update Admin Successfully")
            .isSuccess(true)
            .data(result)
            .build();
        return ResponseEntity.ok(responseObject);
    }

    @PatchMapping("disable/{id}")
    public ResponseEntity<ResponseObject> disableUser(@PathVariable BigDecimal id) {
        var result = adminService.removeUser(id);
        var responseObject = ResponseObject
            .builder()
            .code("DISABLE_ADMIN_SUCCESS")
            .message("Disable Admin Successfully")
            .isSuccess(true)
            .data(result)
            .build();
        return ResponseEntity.ok(responseObject);
    }

    @PatchMapping("locked/{id}")
    public ResponseEntity<ResponseObject> updateUserLock(@PathVariable BigDecimal id, @RequestParam(name = "locked") Boolean isLocked) {
        var result = adminService.changeUserLockStatus(id, isLocked);
        var responseObject = ResponseObject
            .builder()
            .code("CHANGE_ADMIN_LOCKED_SUCCESS")
            .message("Change Admin Lock Successfully")
            .isSuccess(true)
            .data(result)
            .build();
        return ResponseEntity.ok(responseObject);
    }
    @PatchMapping("activate/{id}")
    public ResponseEntity<ResponseObject> activeAccount(@PathVariable(name = "id") BigDecimal id) {
        var result = adminService.activeAccount(id);
        var responseObject = ResponseObject
            .builder()
            .code("ACTIVE_ADMIN_SUCCESS")
            .message("Active Admin Successfully")
            .isSuccess(true)
            .data(result)
            .build();
        return ResponseEntity.ok(responseObject);
    }

}
