package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.response.ProductMenuDetail;

public interface ProductMenuService {
    ProductMenuDetail addProductMenu(ProductMenuRequest productMenuRequest);
    ProductMenuDetail updateProductMenu(ProductMenuRequest productMenuRequest);
}
