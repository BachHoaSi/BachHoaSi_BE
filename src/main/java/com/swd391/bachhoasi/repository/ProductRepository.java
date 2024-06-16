package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends BaseBachHoaSiRepository<Product,BigDecimal>{


    Page<Product> findByCategoryIdAndNameContainingIgnoreCase(BigDecimal categoryId, String search, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String search, Pageable pageable);
    Optional<Product> findByProductCode(String code);

}
