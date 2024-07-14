package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderRepository extends BaseBachHoaSiRepository<Order, BigDecimal> {
    default Page<Order> searchByParameterAndShipperId(Map<String, String> param, Pageable pageable, BigDecimal shipperId) {
        Specification<Order> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (param != null && !param.isEmpty()) {
                for (Map.Entry<String, String> item : param.entrySet()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(item.getKey()).as(String.class)), "%" + item.getValue().toLowerCase() + "%"));
                }
            }

            // Add shipperId condition
            predicates.add(criteriaBuilder.equal(root.get("shipper").get("id"), shipperId));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(spec, pageable);
    }
}
