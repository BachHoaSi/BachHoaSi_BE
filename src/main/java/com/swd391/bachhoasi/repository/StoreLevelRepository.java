package com.swd391.bachhoasi.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swd391.bachhoasi.model.entity.StoreLevel;

import jakarta.persistence.criteria.Predicate;

public interface StoreLevelRepository extends JpaRepository<StoreLevel, BigDecimal>, JpaSpecificationExecutor<StoreLevel> {

    default Specification<StoreLevel> searchStoreLevelByParameterSpecification(Map<String, String> param) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(Entry<String,String> item : param.entrySet()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(item.getKey()).as(String.class)), "%" + item.getValue().toLowerCase() + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
    
    default Page<StoreLevel> searchStoreLevelByParameter(Map<String, String> param, Pageable pageable) {
        Specification<StoreLevel> spec = searchStoreLevelByParameterSpecification(param);
        return findAll(spec,pageable);
    }

}
