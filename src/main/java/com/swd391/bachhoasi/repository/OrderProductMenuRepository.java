package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.OrderProductMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderProductMenuRepository extends BaseBachHoaSiRepository<OrderProductMenu, BigDecimal> {
    @Query("SELECT opm FROM OrderProductMenu opm WHERE opm.order.id = :orderId")
    List<OrderProductMenu> findByOrderId(BigDecimal orderId);}