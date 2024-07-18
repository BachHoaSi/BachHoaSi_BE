package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.ProductMenuDTO;
import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.*;
import com.swd391.bachhoasi.model.entity.Product;
import com.swd391.bachhoasi.model.entity.ProductMenu;
import com.swd391.bachhoasi.model.entity.ProductMenuId;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.repository.MenuRepository;
import com.swd391.bachhoasi.repository.ProductMenuRepository;
import com.swd391.bachhoasi.repository.ProductRepository;
import com.swd391.bachhoasi.service.ProductMenuService;
import com.swd391.bachhoasi.util.AuthUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMenuServiceImpl implements ProductMenuService {
    private final ProductMenuRepository productMenuRepository;
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final AuthUtils authUtils;
    @Override
    public ProductMenuDetail updateProductMenu(BigDecimal menuId, ProductMenuRequest productMenuRequest) {
        if (productMenuRequest == null)
            throw new NotFoundException("ProductMenu request is null");
        var productMenuId = new ProductMenuId();
        productMenuId.setMenu(menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Menu not found")));
        productMenuId.setProduct(productRepository.findById(productMenuRequest.getProductId()).orElseThrow(() -> new NotFoundException("product not found")));
        var productMenu = productMenuRepository.findById(productMenuId).orElseThrow(() -> new NotFoundException("Product menu not found"));
        productMenu.setStatus(productMenuRequest.getStatus());
        productMenu.setUpdatedDate(new Date(System.currentTimeMillis()));
        productMenu.setBasePrice(productMenuRequest.getPrice());
        ProductMenu updatedProductMenu = productMenuRepository.save(productMenu);
        return ProductMenuDetail.builder()
                .status(updatedProductMenu.getStatus())
                .basePrice(updatedProductMenu.getBasePrice())
                .menuId(updatedProductMenu.getComposeId().getMenu().getId())
                .adminName(updatedProductMenu.getAdmin().getFullName())
                .productDetails(mapToProductResponse(updatedProductMenu.getComposeId().getProduct()))
                .build();
    }

    @Override
    public PaginationResponse<ProductMenuResponse> getProductMenues(SearchRequestParamsDto request) {
        try {
            Page<ProductMenuResponse> orderPage = productMenuRepository.searchAnyByParameter(request.search(), request.pagination())
                    .map(item -> ProductMenuResponse.builder()
                            .id(item.getId())
                            .menuId(item.getComposeId().getMenu().getId())
                            .productId(item.getComposeId().getProduct().getId())
                            .basePrice(item.getBasePrice())
                            .status(item.getStatus())
                            .adminName(item.getAdmin().getFullName())
                            .productDetails(mapToProductResponse(item.getComposeId().getProduct()))
                            .build());
            return new PaginationResponse<>(orderPage);
        } catch (Exception ex ) {
            throw new ActionFailedException(ex.getMessage(), "ORDER_GET_FAILED");
        }
    }

    @Override
    public List<ProductMenuDTO> getAvailableProductMenu(String name) {
        List<ProductMenu> productMenus = productMenuRepository.listAvailable(name);
        List<ProductMenuDTO> productMenuDTOS = new ArrayList<>();

        for (ProductMenu productMenu : productMenus) {
            var product = productMenu.getComposeId().getProduct();
            ProductMenuDTO dto = ProductMenuDTO.builder()
                    .id(productMenu.getId())
                    .productName(product.getName())
                    .stockQuantity(product.getStockQuantity())
                    .build();

            productMenuDTOS.add(dto);
        }

        return productMenuDTOS;
    }


    @Override
    public ProductMenuDetail addProductMenu(BigDecimal menuId, ProductMenuRequest productMenuRequest) {

        if (productMenuRequest == null)
            throw new NotFoundException("ProductMenu request is null");
        var productMenuId = new ProductMenuId();
        productMenuId.setMenu(menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Menu not found")));
        productMenuId.setProduct(productRepository.findById(productMenuRequest.getProductId()).orElseThrow(() -> new NotFoundException("Product not found")));
        var productMenu = new ProductMenu();
        productMenu.setComposeId(productMenuId);
        productMenu.setStatus(productMenuRequest.getStatus());
        productMenu.setUpdatedDate(new Date(System.currentTimeMillis()));
        productMenu.setBasePrice(productMenuRequest.getPrice());
        productMenu.setAdmin(authUtils.getAdminUserFromAuthentication());
        ProductMenu updatedProductMenu = productMenuRepository.save(productMenu);
        return ProductMenuDetail.builder()
                .status(updatedProductMenu.getStatus())
                .basePrice(updatedProductMenu.getBasePrice())
                .menuId(updatedProductMenu.getComposeId().getMenu().getId())
                .adminName(updatedProductMenu.getAdmin().getFullName())
                .productDetails(mapToProductResponse(updatedProductMenu.getComposeId().getProduct()))
                .build();
    }

    public static ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .name(product.getName())
                .basePrice(product.getBasePrice())
                .urlImages(product.getUrlImages())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }
}
