package com.swd391.bachhoasi.service;

import java.math.BigDecimal;

import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.response.ProductMenuDetail;

public interface ProductMenuService {
    ProductMenuDetail addProductMenu(BigDecimal menuId,ProductMenuRequest productMenuRequest);
    ProductMenuDetail updateProductMenu(BigDecimal menuId, ProductMenuRequest productMenuRequest);
}
