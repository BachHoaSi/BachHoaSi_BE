package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.OrderProductMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderProductMenuRepository extends BaseBachHoaSiRepository<OrderProductMenu, BigDecimal> {
    @Query("SELECT opm FROM OrderProductMenu opm WHERE opm.order.id = :orderId")
    List<OrderProductMenu> findByOrderId(BigDecimal orderId);
    @Query("SELECT opm.product.id, opm.product.composeId.product.name, SUM(opm.quantity) AS totalQuantity " +
            "FROM OrderProductMenu opm " +
            "GROUP BY opm.product.id, opm.product.composeId.product.name " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findProductIdNameTotalQuantityOrderByTotalQuantityDesc();

}

