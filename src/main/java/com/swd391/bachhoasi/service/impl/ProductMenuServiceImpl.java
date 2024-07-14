package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.response.ProductMenuDetail;
import com.swd391.bachhoasi.model.dto.response.ProductResponse;
import com.swd391.bachhoasi.model.entity.Product;
import com.swd391.bachhoasi.model.entity.ProductMenu;
import com.swd391.bachhoasi.model.entity.ProductMenuId;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.repository.MenuRepository;
import com.swd391.bachhoasi.repository.ProductMenuRepository;
import com.swd391.bachhoasi.repository.ProductRepository;
import com.swd391.bachhoasi.service.ProductMenuService;
import com.swd391.bachhoasi.util.AuthUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class ProductMenuServiceImpl implements ProductMenuService {
    private final ProductMenuRepository productMenuRepository;
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final AuthUtils authUtils;
    @Override
    public ProductMenuDetail updateProductMenu(ProductMenuRequest productMenuRequest) {
        if (productMenuRequest == null)
            throw new NotFoundException("ProductMenu request is null");
        var productMenuId = new ProductMenuId();
        productMenuId.setMenu(menuRepository.findById(productMenuRequest.getMenuId()).orElseThrow(() -> new NotFoundException("menu not found")));
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
    public ProductMenuDetail addProductMenu(ProductMenuRequest productMenuRequest) {
        if (productMenuRequest == null)
            throw new NotFoundException("ProductMenu request is null");
        var productMenuId = new ProductMenuId();
        productMenuId.setMenu(menuRepository.findById(productMenuRequest.getMenuId()).orElseThrow(() -> new NotFoundException("Menu not found")));
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
