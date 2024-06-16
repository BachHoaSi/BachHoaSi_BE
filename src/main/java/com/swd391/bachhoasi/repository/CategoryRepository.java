package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Category;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseBachHoaSiRepository<Category, BigDecimal> {
}
