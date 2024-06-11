package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.ProductRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface ProductService {

    PaginationResponse<ProductResponse> getProducts(Pageable pagination, Map<String,String> parameters);

    ProductResponse addNewProduct(ProductRequest request);

    void deleteProduct(String code);

    ProductResponse updateProduct(ProductRequest request, String code);
}
