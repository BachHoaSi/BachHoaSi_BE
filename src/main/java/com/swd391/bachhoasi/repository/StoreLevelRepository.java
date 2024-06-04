package com.swd391.bachhoasi.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swd391.bachhoasi.model.entity.StoreLevel;

public interface StoreLevelRepository extends JpaRepository<StoreLevel, BigDecimal> {
    
}
