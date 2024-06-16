package com.swd391.bachhoasi.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseBachHoaSiRepository<T, K extends Serializable> extends JpaRepository<T, K>, JpaSpecificationExecutor<T> {
    default Specification<T> searchByParameterAnySpecification(Map<String, String> param) {
        return (root, query, criteriaBuilder) -> {
            if (param == null || param.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            } else {
                List<Predicate> predicates = new ArrayList<>();
                for(Map.Entry<String,String> item : param.entrySet()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(item.getKey()).as(String.class)), "%" + item.getValue().toLowerCase() + "%"));
                }
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
            
        };
    }

    default Specification<T> searchByParameterSpecification(Map<String, String> param) {
        return (root, query, criteriaBuilder) -> {
            if (param == null || param.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            } else {
                List<Predicate> predicates = new ArrayList<>();
                for(Map.Entry<String,String> item : param.entrySet()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(item.getKey()).as(String.class)), "%" + item.getValue().toLowerCase() + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
    default Page<T> searchByParameter(Map<String, String> param, Pageable pageable) {
        Specification<T> spec = searchByParameterSpecification(param);
        return findAll(spec,pageable);
    }

    default Page<T> searchAnyByParameter(Map<String, String> param, Pageable pageable) {
        Specification<T> spec = searchByParameterAnySpecification(param);
        return findAll(spec,pageable);
    }
}
