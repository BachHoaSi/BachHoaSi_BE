package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.ProductRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductResponse;
import com.swd391.bachhoasi.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ProductService {

    PaginationResponse<ProductResponse> getProducts(int pageNumber, int pageSize, String search, BigDecimal categoryId, String sort, String direction);
    ProductResponse addNewProduct(ProductRequest request);

    void deleteProduct(String code);

    ProductResponse updateProduct(ProductRequest request, String code);
}
