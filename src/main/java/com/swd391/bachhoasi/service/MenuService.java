package com.swd391.bachhoasi.service;

import java.math.BigDecimal;

import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.response.MenuDetailResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductMenuDetail;

public interface MenuService {
    public PaginationResponse<MenuDetailResponse> initStoreMenu();
    public ProductMenuDetail addProductToMenu(BigDecimal menuId, ProductMenuRequest request);
}
