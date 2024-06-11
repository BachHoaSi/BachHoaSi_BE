package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface StoreRepository extends JpaRepository<Store, BigDecimal> {

    Page<Store> findByNameContainingIgnoreCase(String search, Pageable pageable);




}
