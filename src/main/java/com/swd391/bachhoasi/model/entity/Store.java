package com.swd391.bachhoasi.model.entity;

import java.math.BigDecimal;

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
@Entity(name = "Store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id")
    private BigDecimal id;
    @Column(name = "Name", columnDefinition = "varchar", length = 64)
    private String name;
    @ManyToOne(targetEntity = StoreType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "Type")
    private StoreType type;
    @Column(name = "Point")
    private Integer point;
    @Column(name = "Status")
    private Boolean status;
    @Column(name = "Location", columnDefinition = "text")
    private String location;
    @ManyToOne(targetEntity = StoreLevel.class, optional = false)
    @JoinColumn(name = "StoreLevelId")
    private StoreLevel storeLevel;
}
