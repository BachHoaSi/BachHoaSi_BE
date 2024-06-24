package com.swd391.bachhoasi.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

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
@Entity(name = "Product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", columnDefinition = "BIGINT")
    private BigDecimal id;
    @Column(name = "Name", columnDefinition = "varchar", length = 100)
    private String name;
    @Column(name = "BasePrice")
    private BigDecimal basePrice;
    @Column(name = "Description", columnDefinition = "text")
    private String description;
    @Column(name = "StockQuantity")
    private Integer stockQuantity;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "LastImportDate", nullable = true)
    private Date lastImportDate;
    @Column(name = "UrlImages")
    private String urlImages;
    @Column(name = "ProductCode")
    private String productCode;
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "CategoryId")
    private Category category;
    @Column(name = "Status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;
}
