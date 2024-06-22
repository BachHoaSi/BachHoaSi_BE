package com.swd391.bachhoasi.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.dto.response.MenuDetailResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.entity.Menu;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.repository.MenuRepository;
import com.swd391.bachhoasi.repository.ProductMenuRepository;
import com.swd391.bachhoasi.repository.StoreLevelRepository;
import com.swd391.bachhoasi.repository.StoreTypeRepository;
import com.swd391.bachhoasi.service.MenuService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ProductMenuRepository productMenuRepository;
    private final StoreLevelRepository storeLevelRepository;
    private final StoreTypeRepository storeTypeRepository;
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

    public void addingProductToMenu() {
        
    }
}
