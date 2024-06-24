package com.swd391.bachhoasi.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Menu")
public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private BigDecimal id;
    @ManyToOne(targetEntity = StoreLevel.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "StoreLevelId")
    private StoreLevel storeLevel;
    @ManyToOne(targetEntity = StoreType.class, optional = false)
    private StoreType storeType;
    @Column(name = "Status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;
    @CreationTimestamp(source = SourceType.DB)
    private Date createdDate;
    @ManyToOne(targetEntity = Admin.class, optional = false)
    @JoinColumn(name = "CreatedBy")
    private Admin createdBy;
    @UpdateTimestamp(source = SourceType.DB)
    private Date updatedDate;
    @ManyToOne(targetEntity = Admin.class, optional = false)
    @JoinColumn(name = "UpdatedBy")
    private Admin updatedBy;
}
