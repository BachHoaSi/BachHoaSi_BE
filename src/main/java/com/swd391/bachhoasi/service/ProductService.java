package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.ProductRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductResponse;

import org.springframework.stereotype.Service;


@Service
public interface ProductService {

    PaginationResponse<ProductResponse> getProducts(SearchRequestParamsDto request);

    ProductResponse addNewProduct(ProductRequest request);

    void deleteProduct(String code);

    ProductResponse updateProduct(ProductRequest request, String code);
}
