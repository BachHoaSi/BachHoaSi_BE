package com.swd391.bachhoasi.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.swd391.bachhoasi.model.constant.CartStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", columnDefinition = "BIGSERIAL")
    private BigDecimal id;
    @OneToOne(targetEntity = Store.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "StoreId")
    private Store store;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "Status")
    private CartStatus cartStatus;
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "UpdatedDate")
    private Date updatedDate;
    @OneToMany(targetEntity = CartProductMenu.class,mappedBy = "cart", fetch = FetchType.EAGER)
    private List<CartProductMenu> cartProducts;
}
