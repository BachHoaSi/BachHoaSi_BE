package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AdminRepository extends JpaRepository<Admin, BigDecimal> {

    Admin findByUsername(String username);
}
