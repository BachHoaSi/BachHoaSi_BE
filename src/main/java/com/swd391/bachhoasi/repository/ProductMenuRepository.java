package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.dto.request.ProductMenuDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swd391.bachhoasi.model.entity.ProductMenu;
import com.swd391.bachhoasi.model.entity.ProductMenuId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface ProductMenuRepository extends BaseBachHoaSiRepository<ProductMenu, ProductMenuId>{
    @Query("SELECT pm FROM ProductMenu pm " +
            "JOIN pm.composeId.menu m " +
            "JOIN m.storeLevel sl " +
            "JOIN m.storeType st " +
            "JOIN pm.composeId.product p " +
            "WHERE p.status = true AND pm.status = true AND pm.id IN :subIds")
    List<ProductMenu> findBySubIds(@Param("subIds") List<BigDecimal> subIds);

    @Query("SELECT pm " +
            "FROM ProductMenu pm " +
            "WHERE pm.status = true " +
            "AND pm.composeId.product.stockQuantity > 0")
    List<ProductMenu> listAvalilble();
}