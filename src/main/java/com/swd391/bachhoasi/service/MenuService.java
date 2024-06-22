package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.response.MenuDetailResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;

public interface MenuService {
    public PaginationResponse<MenuDetailResponse> initStoreMenu();
}
