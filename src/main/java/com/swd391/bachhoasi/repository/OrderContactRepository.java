package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.OrderContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface OrderContactRepository extends BaseBachHoaSiRepository<OrderContact, BigDecimal> {
}
