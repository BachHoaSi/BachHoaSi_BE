package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Order;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface OrderRepository extends BaseBachHoaSiRepository<Order, BigDecimal> {
}