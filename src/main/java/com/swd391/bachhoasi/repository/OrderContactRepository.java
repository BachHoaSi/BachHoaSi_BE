package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.OrderContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface OrderContactRepository extends JpaRepository<OrderContact, BigDecimal> {
}
