package com.swd391.bachhoasi.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.swd391.bachhoasi.model.entity.Shipper;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ShipperRepository extends BaseBachHoaSiRepository<Shipper, BigDecimal> {
    @Query("select s from Shipper s where s.email = :email")
    Optional<Shipper> findByEmail(String email);
}
