package com.swd391.bachhoasi.model.entity;

import java.math.BigDecimal;

import com.swd391.bachhoasi.model.constant.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id")
    private BigDecimal id;
    @Column(name = "UserName", nullable = false, columnDefinition = "varchar", length = 32)
    private String username;
    @Column(name = "HashPassword", nullable = false, columnDefinition = "varchar", length = 64)
    private String hashPassword;
    @Column(name = "Role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
