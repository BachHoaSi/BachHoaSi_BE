package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,BigDecimal> {

    Optional<Category> findById(BigDecimal id);
}
