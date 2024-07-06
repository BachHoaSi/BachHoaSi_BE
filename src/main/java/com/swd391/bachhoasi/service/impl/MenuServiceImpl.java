package com.swd391.bachhoasi.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.response.MenuDetailResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductMenuDetail;
import com.swd391.bachhoasi.model.entity.Menu;
import com.swd391.bachhoasi.model.entity.Product;
import com.swd391.bachhoasi.model.entity.ProductMenu;
import com.swd391.bachhoasi.model.entity.ProductMenuId;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.repository.MenuRepository;
import com.swd391.bachhoasi.repository.ProductMenuRepository;
import com.swd391.bachhoasi.repository.ProductRepository;
import com.swd391.bachhoasi.repository.StoreLevelRepository;
import com.swd391.bachhoasi.repository.StoreTypeRepository;
import com.swd391.bachhoasi.service.MenuService;
import com.swd391.bachhoasi.util.AuthUtils;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ProductMenuRepository productMenuRepository;
    private final ProductRepository productRepository;
    private final StoreLevelRepository storeLevelRepository;
    private final StoreTypeRepository storeTypeRepository;
    private final AuthUtils authUtils;
    @Override
    public PaginationResponse<MenuDetailResponse> initStoreMenu() {
        var storeLevelList = storeLevelRepository.findAll();
        var storeTypeList = storeTypeRepository.findAll();
        if (storeLevelList.isEmpty() || storeTypeList.isEmpty()) {
            throw new ValidationFailedException("Should have store level or store type in system, skipped !!!");
        }
        Collection<Menu> menuList = new ArrayList<>();
        storeLevelList.forEach(storeLevel -> {
            storeTypeList.forEach(storeType -> {
                var temp = new Menu();
                temp.setStoreLevel(storeLevel);
                temp.setStoreType(storeType);
                temp.setStatus(true);
                menuList.add(temp);
            });
        });
        try {
            var result = menuRepository.saveAllAndFlush(menuList);
            var finalResult = result.stream().map(item -> MenuDetailResponse.builder()
                    .id(item.getId())
                    .level(item.getStoreLevel().getLevel())
                    .type(item.getStoreType().getName())
                    .status(item.getStatus())
                    .build()
            ).toList();
            return new PaginationResponse<>(finalResult);
        } catch (Exception ex ) {
            throw new ActionFailedException(String.format("Can't init menu with reason: %s", ex.toString()));
        }
    }
    @Override
    public ProductMenuDetail addProductToMenu(BigDecimal menuId, ProductMenuRequest request) {
        var menu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Not found menu"));
        var product = productRepository.findById(request.getProductId()).orElseThrow(() -> new NotFoundException("Not found product"));
        var productMenuEntity = convertToProductMenuEntity(request, product, menu);
        var result = productMenuRepository.save(productMenuEntity);
        return convertProductMenuToProductMenuDetailDto(result);
    }

    private ProductMenu convertToProductMenuEntity(ProductMenuRequest request, Product product, Menu menu) {
        var productMenuId = ProductMenuId.builder()
                .menu(menu)
                .product(product)
                .build();
        var admin = authUtils.getAdminUserFromAuthentication();
        return ProductMenu.builder()
                .composeId(productMenuId)
                .basePrice(request.getPrice())
                .admin(admin)
                .status(request.getStatus())
                .build();
    }

    public static ProductMenuDetail convertProductMenuToProductMenuDetailDto(ProductMenu productMenu) {
        String adminName = productMenu.getAdmin() != null ? productMenu.getAdmin().getFullName() : "Default";
        var product = productMenu.getComposeId().getProduct();
        return ProductMenuDetail.builder()
                .basePrice(productMenu.getBasePrice())
                .status(productMenu.getStatus())
                .adminName(adminName)
                .productDetails(product != null ? ProductServiceImpl.mapToProductResponse(product):null)
                .build();
    }
}