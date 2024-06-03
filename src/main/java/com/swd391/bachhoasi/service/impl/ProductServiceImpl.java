package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.ProductRequest;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ProductResponse;
import com.swd391.bachhoasi.model.entity.Category;
import com.swd391.bachhoasi.model.entity.Product;
import com.swd391.bachhoasi.repository.CategoryRepository;
import com.swd391.bachhoasi.repository.ProductRepository;
import com.swd391.bachhoasi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    private final SecureRandom RANDOM = new SecureRandom();

    @Override
    public PaginationResponse<ProductResponse> getProducts(int pageNumber, int pageSize, String search, BigDecimal categoryId, String sort, String direction) {
        Sort.Direction sortDirection = (direction != null && direction.equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sortParameters = (sort != null) ? Sort.by(sortDirection, sort) : Sort.unsorted();
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sortParameters);
        if(categoryId == null || categoryId.equals(BigDecimal.ZERO)){
            Page<Product> products = productRepository.findByNameContainingIgnoreCase(search, pageable);
            return new PaginationResponse<>(products.map(this::mapToProductResponse));
        }
        Page<Product> products = productRepository.findByCategoryIdAndNameContainingIgnoreCase(categoryId, search, pageable);
        return new PaginationResponse<>(products.map(this::mapToProductResponse));
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
                .productCode(generateProductCode())
                .createdDate(new Date(System.currentTimeMillis()))
                .lastImportDate(new Date(System.currentTimeMillis()))
                .category(category.get())
                .build();
        Product savedProduct = productRepository.save(newProduct);
        return mapToProductResponse(savedProduct);
    }

    @Override
    public void deleteProduct(String code) {
        Product deleteProduct = productRepository.findByProductCode(code);
        if(deleteProduct == null){
            throw new IllegalArgumentException("Product not found");
        }else {
            try{
                productRepository.delete(deleteProduct);
            }catch (Exception e){
                throw new IllegalArgumentException("Product is in use");
            }
        }
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request, String code) {
        Product product = productRepository.findByProductCode(code);
        if(product == null){
            throw new IllegalArgumentException("Product not found");
        }
        Field[] fields = ProductRequest.class.getDeclaredFields();
        for(Field field : fields){
            try{
                field.setAccessible(true);
                Object value = field.get(request);
                if(value != null){
                    if(field.getName().equalsIgnoreCase("categoryId")){
                        Optional<Category> category = categoryRepository.findById((BigDecimal) value);
                        updateProductField(product,field.getName(), category);
                    }else {
                        updateProductField(product,field.getName(),value);
                    }
                }
            }catch (IllegalAccessException e){
                throw new IllegalArgumentException("Invalid field name");
            }
        }
        Product updatedProduct = productRepository.save(product);
        return mapToProductResponse(updatedProduct);
    }

    private void updateProductField(Product student, String fieldName, Object value) {
        try {
            Field productField = Product.class.getDeclaredField(fieldName);
            productField.setAccessible(true);
            productField.set(student, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Invalid field name");
        }
    }

    private String generateProductCode() {
        // Prefix
        String prefix = "BHS";
        // Generate a sequence of 10 random digits
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randomDigit = RANDOM.nextInt(10); // Generates a random digit between 0 and 9
            stringBuilder.append(randomDigit);
        }
        return prefix + stringBuilder;
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
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
