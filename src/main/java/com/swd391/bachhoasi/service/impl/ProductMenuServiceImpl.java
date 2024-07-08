package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.response.ProductMenuDetail;
import com.swd391.bachhoasi.model.dto.response.ProductResponse;
import com.swd391.bachhoasi.model.entity.Product;
import com.swd391.bachhoasi.model.entity.ProductMenu;
import com.swd391.bachhoasi.model.entity.ProductMenuId;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.repository.AdminRepository;
import com.swd391.bachhoasi.repository.MenuRepository;
import com.swd391.bachhoasi.repository.ProductMenuRepository;
import com.swd391.bachhoasi.repository.ProductRepository;
import com.swd391.bachhoasi.service.ProductMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductMenuServiceImpl implements ProductMenuService {
    private final ProductMenuRepository productMenuRepository;
    private final AdminRepository adminRepository;
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    @Override
    public ProductMenuDetail updateProductMenu(ProductMenuRequest productMenuRequest) {
        if (productMenuRequest == null)
            throw  new NullPointerException("productMenuRequest is null");
        var productMenuId = new ProductMenuId();
        productMenuId.setMenu(menuRepository.findById(productMenuRequest.getMenuId()).orElseThrow(() -> new NotFoundException("menu not found")));
        productMenuId.setProduct(productRepository.findById(productMenuRequest.getProductId()).orElseThrow(() -> new NotFoundException("product not found")));
        var productMenu = productMenuRepository.findById(productMenuId).orElseThrow(() -> new NotFoundException("Product menu not found"));
        productMenu.setStatus(productMenuRequest.getStatus());
        productMenu.setUpdatedDate(new Date(System.currentTimeMillis()));
        productMenu.setBasePrice(productMenuRequest.getPrice());
        ProductMenu updatedProductMenu = productMenuRepository.save(productMenu);
        return ProductMenuDetail.builder()
                .status(productMenu.getStatus())
                .basePrice(productMenu.getBasePrice())
                .menuId(productMenu.getComposeId().getMenu().getId())
                .adminName(productMenu.getAdmin().getFullName())
                .productDetails(mapToProductResponse(productMenu.getComposeId().getProduct()))
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
