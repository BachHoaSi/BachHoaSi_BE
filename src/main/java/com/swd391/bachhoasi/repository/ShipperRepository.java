package com.swd391.bachhoasi.repository;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.swd391.bachhoasi.model.entity.Shipper;

@Repository
public interface ShipperRepository extends BaseBachHoaSiRepository<Shipper, BigDecimal> {
    
}
