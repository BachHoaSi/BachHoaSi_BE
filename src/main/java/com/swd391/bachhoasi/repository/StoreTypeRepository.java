package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.StoreType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface StoreTypeRepository extends JpaRepository<StoreType, BigDecimal> {
    Page<StoreType> findByName(String name, Pageable pageable);
    Page<StoreType> findByDescription(String description, Pageable pageable);
        @Query("select s from StoreType s where s.name like %:keyword% and s.description like %:keyword%")
        Page<StoreType> findByNameOrDescription(String keyword, Pageable pageable);
/*
    Optional<StoreType> findById(BigDecimal id);
*/
}
