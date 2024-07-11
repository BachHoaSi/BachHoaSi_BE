package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.response.ProductMenuDetail;
import com.swd391.bachhoasi.model.entity.ProductMenuId;

import java.math.BigDecimal;

public interface ProductMenuService {
    ProductMenuDetail updateProductMenu(ProductMenuRequest productMenuRequest);
}
