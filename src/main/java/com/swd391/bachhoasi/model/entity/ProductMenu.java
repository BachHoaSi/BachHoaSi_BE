package com.swd391.bachhoasi.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
@Entity(name = "ProductMenu")
@Data
@Builder
public class ProductMenu implements Serializable {
    @EmbeddedId
    private ProductMenuId composeId;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id",columnDefinition = "BIGSERIAL", nullable = false, unique = true)
    private BigDecimal id;
    @Column(name = "Price")
    private BigDecimal basePrice;
    @Column(name = "CreatedDate")
    @UpdateTimestamp(source = SourceType.DB)
    private Date createdDate;
    @Column(name = "UpdatedDate")
    @UpdateTimestamp(source = SourceType.DB)
    private Date updatedDate;
    @Column(name = "Status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;
    @ManyToOne(targetEntity = Admin.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "AdminId", nullable = false)
    private Admin admin;
}
