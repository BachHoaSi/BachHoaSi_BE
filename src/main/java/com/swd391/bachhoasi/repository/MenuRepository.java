package com.swd391.bachhoasi.repository;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.swd391.bachhoasi.model.entity.Menu;

@Repository
public interface MenuRepository extends BaseBachHoaSiRepository<Menu, BigDecimal> {
    
}
