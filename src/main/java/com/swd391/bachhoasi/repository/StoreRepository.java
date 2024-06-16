package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface StoreRepository extends BaseBachHoaSiRepository<Store, BigDecimal> {

    Page<Store> findByNameContainingIgnoreCase(String search, Pageable pageable);




}
