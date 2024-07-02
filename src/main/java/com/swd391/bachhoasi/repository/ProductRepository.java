package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Category;
import com.swd391.bachhoasi.model.entity.Product;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProductRepository extends BaseBachHoaSiRepository<Product,BigDecimal>{


    Page<Product> findByCategoryIdAndNameContainingIgnoreCase(BigDecimal categoryId, String search, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String search, Pageable pageable);
    Optional<Product> findByProductCode(String code);

    default Specification<Product> searchProductByParameterAnySpecification(Map<String, String> param) {
        return (root, query, criteriaBuilder) -> {
            if (param == null || param.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            } else {
                List<Predicate> predicates = new ArrayList<>();
                Join<Product,Category> categoryJoin = root.join("category");
                for(Map.Entry<String,String> item : param.entrySet()) {
                    if (item.getKey().equals("categoryName")) {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(categoryJoin.get("name").as(String.class)),
                                "%" + item.getValue().toLowerCase() + "%"));
                    } else {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(item.getKey()).as(String.class)),
                                "%" + item.getValue().toLowerCase() + "%"));
                    }
                }
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
            
        };
    }

    default Specification<Product> searchProductByParameterSpecification(Map<String, String> param) {
        return (root, query, criteriaBuilder) -> {
            if (param == null || param.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            } else {
                List<Predicate> predicates = new ArrayList<>();
                Join<Product,Category> categoryJoin = root.join("category");
                for(Map.Entry<String,String> item : param.entrySet()) {
                    if (item.getKey().equals("categoryName")) {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(categoryJoin.get("name").as(String.class)),
                                "%" + item.getValue().toLowerCase() + "%"));
                    } else {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(item.getKey()).as(String.class)),
                                "%" + item.getValue().toLowerCase() + "%"));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
            
        };
    }

    default Page<Product> searchProductAnyByParameter(Map<String, String> param, Pageable pageable) {
        Specification<Product> spec = searchProductByParameterAnySpecification(param);
        return findAll(spec,pageable);
    }
    default Page<Product> searchProductByParameter(Map<String, String> param, Pageable pageable) {
        Specification<Product> spec = searchProductByParameterSpecification(param);
        return findAll(spec,pageable);
    }
}
