package com.swd391.bachhoasi.service;

import java.math.BigDecimal;
import java.util.List;

import com.swd391.bachhoasi.model.dto.request.ProductMenuDTO;
import com.swd391.bachhoasi.model.dto.request.ProductMenuRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductMenuDetail;

public interface ProductMenuService {
    ProductMenuDetail addProductMenu(BigDecimal menuId,ProductMenuRequest productMenuRequest);
    ProductMenuDetail updateProductMenu(BigDecimal menuId, ProductMenuRequest productMenuRequest);
    PaginationResponse<ProductMenuDetail> getProductMenues(SearchRequestParamsDto request);
    List<ProductMenuDTO> getAvailableProductMenu();
}
