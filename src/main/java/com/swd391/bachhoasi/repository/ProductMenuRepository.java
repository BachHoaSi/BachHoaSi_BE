package com.swd391.bachhoasi.repository;

import org.springframework.stereotype.Repository;

import com.swd391.bachhoasi.model.entity.ProductMenu;
import com.swd391.bachhoasi.model.entity.ProductMenuId;

@Repository
public interface ProductMenuRepository extends BaseBachHoaSiRepository<ProductMenu, ProductMenuId>{
    
}
