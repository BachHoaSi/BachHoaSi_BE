package com.swd391.bachhoasi.repository;

import com.swd391.bachhoasi.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, BigDecimal> {

    Optional<Admin> findByUsername(String username);
}
