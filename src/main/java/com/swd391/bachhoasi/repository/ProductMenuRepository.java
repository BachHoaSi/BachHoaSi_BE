package com.swd391.bachhoasi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swd391.bachhoasi.model.entity.ProductMenu;
import com.swd391.bachhoasi.model.entity.ProductMenuId;

import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
       "AND pm.composeId.product.stockQuantity > 0 " +
       "AND (:name IS NULL OR pm.composeId.product.name LIKE %:name%)")
    List<ProductMenu> listAvailable(@Param("name") String name);

    default Specification<ProductMenu> searchByParameterAndMenuIdSpecification(Map<String, String> param, BigDecimal menuId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (param != null && !param.isEmpty()) {
                for (Map.Entry<String, String> item : param.entrySet()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(item.getKey()).as(String.class)), "%" + item.getValue().toLowerCase() + "%"));
                }
            }
            predicates.add(criteriaBuilder.equal(root.get("composeId").get("menu").get("id"), menuId));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    default Page<ProductMenu> searchByParameterAndMenuId(Map<String, String> param, Pageable pageable, BigDecimal menuId) {
        Specification<ProductMenu> spec = searchByParameterAndMenuIdSpecification(param, menuId);
        return findAll(spec, pageable);
    }
}