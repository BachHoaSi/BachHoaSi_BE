package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.ProductRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductResponse;
import com.swd391.bachhoasi.model.entity.Category;
import com.swd391.bachhoasi.model.entity.Product;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.repository.CategoryRepository;
import com.swd391.bachhoasi.repository.ProductRepository;
import com.swd391.bachhoasi.service.ProductService;
import com.swd391.bachhoasi.util.BaseUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public PaginationResponse<ProductResponse> getProducts(SearchRequestParamsDto request) {
        try {
            Page<ProductResponse> products = productRepository.searchAnyByParameter(request.search(), request.pagination())
                    .map(item -> ProductResponse.builder()
                            .id(item.getId())
                            .productCode(item.getProductCode())
                            .name(item.getName())
                            .basePrice(item.getBasePrice())
                            .urlImages(item.getUrlImages())
                            .description(item.getDescription())
                            .stockQuantity(item.getStockQuantity())
                            .categoryName(item.getCategory().getName())
                            .build());
            return new PaginationResponse<>(products);
        } catch (Exception ex ) {
            throw new ActionFailedException(ex.getMessage(), "STORE_LEVEL_GET_FAILED");
        }

    }

    @Override
    public ProductResponse addNewProduct(ProductRequest product) {
        Optional<Category> category = categoryRepository.findById(product.getCategoryId());
        if(category.isEmpty()){
            throw new IllegalArgumentException("Category not found");
        }
        Product newProduct = Product.builder()
                .name(product.getName())
                .basePrice(product.getBasePrice())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .urlImages(product.getUrlImages())
                .productCode(BaseUtils.generateProductCode())
                .createdDate(new Date(System.currentTimeMillis()))
                .lastImportDate(new Date(System.currentTimeMillis()))
                .category(category.get())
                .build();
        Product savedProduct = productRepository.save(newProduct);
        return mapToProductResponse(savedProduct);
    }

    @Override
    public void deleteProduct(String code) {
        Product deleteProduct = productRepository.findByProductCode(code).orElseThrow(() -> new NotFoundException(String.format("Not found product with code: %s", code)));
        if(deleteProduct == null){
            throw new IllegalArgumentException("Product not found");
        }else {
            try{
                productRepository.delete(deleteProduct);
            }catch (Exception e){
                throw new ActionFailedException("Product is in use");
            }
        }
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request, String code) {
        Product product = productRepository.findByProductCode(code).orElseThrow(() -> new NotFoundException(String.format("Not found product with code: %s", code)));
        
        product.setName(request.getName());
        product.setBasePrice(request.getBasePrice());
        product.setDescription(request.getDescription());
        product.setStockQuantity(request.getStockQuantity());
        product.setUrlImages(request.getUrlImages());
        if (request.getCategoryId() != null || request.getCategoryId().intValue() > 0) {
            Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() 
            -> new NotFoundException(String.format("Not found category with id: %s", request.getCategoryId())));
            product.setCategory(category);
        }
        Product updatedProduct = productRepository.save(product);
        return mapToProductResponse(updatedProduct);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .name(product.getName())
                .basePrice(product.getBasePrice())
                .urlImages(product.getUrlImages())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }
}
