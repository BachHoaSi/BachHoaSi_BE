package com.swd391.bachhoasi.controller;

import com.swd391.bachhoasi.model.dto.request.ProductRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductResponse;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ResponseObject> getProducts(@PageableDefault(page = 0, size = 20, sort = "productCode", direction = Direction.DESC) Pageable pagination,
                                                      @RequestParam(value = "search", defaultValue = "") String search,
                                                      @RequestParam(value = "category", defaultValue = "") BigDecimal categoryId){
        PaginationResponse<ProductResponse> products = productService.getProducts(pagination, search, categoryId);
        var responseObject = ResponseObject.builder()
            .code("PRODUCT_GET_SUCCESS")
            .message("Get products successfully")
            .status(HttpStatus.OK)
            .isSuccess(true)
            .data(products)
            .build();
        return ResponseEntity.ok().body(responseObject);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> addNewProduct(@RequestBody @Valid ProductRequest product){
        try{
            ProductResponse productResponse = productService.addNewProduct(product);
            var responseObject = ResponseObject.builder()
                    .data(productResponse)
                    .code("PRODUCT_ADD_SUCCESS")
                    .message("Add product successfully")
                    .status(HttpStatus.OK)
                    .isSuccess(true)
                    .build();
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
           var responseObject = ResponseObject.builder()
                   .code("PRODUCT_ADD_FAILED")
                   .message(e.getMessage())
                   .status(HttpStatus.BAD_REQUEST)
                   .isSuccess(false)
                   .build();
           return ResponseEntity.ok(responseObject);
        }

    }
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> deleteProduct(@RequestParam("code") String code){
        try{
            productService.deleteProduct(code);
            var responseObject = ResponseObject.builder()
                    .code("PRODUCT_DELETE_SUCCESS")
                    .message("Delete product successfully")
                    .status(HttpStatus.OK)
                    .isSuccess(true)
                    .build();
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            var responseObject = ResponseObject.builder()
                    .code("PRODUCT_DELETE_FAILED")
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .isSuccess(false)
                    .build();
            return ResponseEntity.ok().body(responseObject);
        }
    }
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody @Valid ProductRequest product, @RequestParam("code") String code){
        try{
            ProductResponse productResponse = productService.updateProduct(product, code);
            var responseObject = ResponseObject.builder()
                    .data(productResponse)
                    .code("PRODUCT_UPDATE_SUCCESS")
                    .message("Update product successfully")
                    .status(HttpStatus.OK)
                    .isSuccess(true)
                    .build();
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            var responseObject = ResponseObject.builder()
                    .code("PRODUCT_UPDATE_FAILED")
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .isSuccess(false)
                    .build();
            return ResponseEntity.ok(responseObject);
        }
    }
}
