package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository extends CustomRepository<Product,BigDecimal>{


    Page<Product> findByCategoryIdAndNameContainingIgnoreCase(BigDecimal categoryId, String search, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String search, Pageable pageable);
    Optional<Product> findByProductCode(String code);

}
