package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.ProductRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ProductService {

    PaginationResponse<ProductResponse> getProducts(Pageable pagination, String search, BigDecimal categoryId);
    ProductResponse addNewProduct(ProductRequest request);

    void deleteProduct(String code);

    ProductResponse updateProduct(ProductRequest request, String code);
}
