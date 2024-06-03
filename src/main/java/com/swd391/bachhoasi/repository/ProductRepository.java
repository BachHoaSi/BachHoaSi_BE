package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, BigDecimal> {

    Page<Product> findByCategoryIdAndNameContainingIgnoreCase(BigDecimal categoryId, String search, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String search, Pageable pageable);
    Product findByProductCode(String code);

}
