package com.swd391.bachhoasi.controller;

import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.response.ProductMenuDetail;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.ProductMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-menus")
@RequiredArgsConstructor
public class ProductMenuController {
    private final ProductMenuService productMenuService;
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody @Valid ProductMenuRequest productMenuRequest) {
        ProductMenuDetail productMenuResponse = productMenuService.updateProductMenu(productMenuRequest);
        var responseObject = ResponseObject.builder()
                .data(productMenuResponse)
                .code("PRODUCT_MENU_UPDATE_SUCCESS")
                .message("Update product menu successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok().body(responseObject);

    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<ResponseObject> importProductMenu(@RequestBody @Valid ProductMenuRequest productMenuRequest) {
        ProductMenuDetail productMenuResponse = productMenuService.addProductMenu(productMenuRequest);
        var responseObject = ResponseObject.builder()
                .data(productMenuResponse)
                .code("PRODUCT_MENU_ADD_SUCCESS")
                .message("Add Product Menu Successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }
}
