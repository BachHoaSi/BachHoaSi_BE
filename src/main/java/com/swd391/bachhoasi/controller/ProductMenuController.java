package com.swd391.bachhoasi.controller;

import com.swd391.bachhoasi.model.dto.request.ProductMenuDTO;
import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.ProductMenuDetail;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.ProductMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<ResponseObject> updateProduct(BigDecimal menuId, @RequestBody @Valid ProductMenuRequest productMenuRequest) {
        ProductMenuDetail productMenuResponse = productMenuService.updateProductMenu(menuId,productMenuRequest);
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
    public ResponseEntity<ResponseObject> importProductMenu(BigDecimal menuId,@RequestBody @Valid ProductMenuRequest productMenuRequest) {
        ProductMenuDetail productMenuResponse = productMenuService.addProductMenu(menuId,productMenuRequest);
        var responseObject = ResponseObject.builder()
                .data(productMenuResponse)
                .code("PRODUCT_MENU_ADD_SUCCESS")
                .message("Add Product Menu Successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getProductMenues(
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pagination,
            @RequestParam(required = false, name = "q") String query
    ) {
        var queryDto = SearchRequestParamsDto.builder()
                .search(query)
                .wrapSort(pagination)
                .build();
        var result = productMenuService.getProductMenues(queryDto);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("PRODUCT_MENU_GET_SUCCESS")
                        .isSuccess(true)
                        .data(result)
                        .message("Get Product Menu Success")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @GetMapping("/available")
    public ResponseEntity<ResponseObject> getProductMenusAvailable(@RequestParam(name = "name", required = false) String name) {
        List<ProductMenuDTO> productMenus = productMenuService.getAvailableProductMenu(name);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("PRODUCT_MENU_GET_SUCCESS")
                        .isSuccess(true)
                        .data(productMenus)
                        .message("Get Product Menu Success")
                        .status(HttpStatus.OK)
                        .build()
        );

    }

    @GetMapping("/{menuID}")
    public ResponseEntity<ResponseObject> getProductMenusAvailable(@PathVariable(name = "menuID") BigDecimal menuId, @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pagination,
    @RequestParam(required = false, name = "q") String query) {
        var queryDto = SearchRequestParamsDto.builder()
                .search(query)
                .wrapSort(pagination)
                .build();
        var productMenus = productMenuService.getProductMenuByMenuIdPagination(menuId, queryDto);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("PRODUCT_MENU_GET_SUCCESS")
                        .isSuccess(true)
                        .data(productMenus)
                        .message("Get Product Menu Success")
                        .status(HttpStatus.OK)
                        .build()
        );

    }
}
