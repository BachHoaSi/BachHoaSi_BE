package com.swd391.bachhoasi.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import com.swd391.bachhoasi.model.constant.ShipperStatus;
import com.swd391.bachhoasi.model.constant.ShippingStatus;
import com.swd391.bachhoasi.model.constant.VehicleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Shipper")
public class Shipper implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id", columnDefinition = "BIGSERIAL")
    private BigDecimal id;
    @Column(name = "Name", columnDefinition = "varchar", length = 48)
    private String name;
    @Column(name = "Phone", columnDefinition = "varchar", length = 12)
    private String phone;
    @Column(name = "Email")
    private String email;
    @Column(name = "HashPassword")
    private String hashPassword;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "Status")
    private ShipperStatus status;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ShippingStatus")
    private ShippingStatus shippingStatus;
    @Column(name = "LicenseNumber", columnDefinition = "varchar", length = 20)
    private String licenseNumber;
    @Column(name = "LicenseIssueDate")
    private Date licenseIssueDate;
    @Column(name = "IdCardNumber", columnDefinition = "varchar", length = 20)
    private String idCardNumber;
    @Column(name = "IdCardIssuePlace", columnDefinition = "varchar", length = 128)
    private String idCardIssuePlace;
    @Column(name = "IdCardIssueDate")
    private Date idCardIssueDate;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "VehicleType")
    private VehicleType vehicleType;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "CreatedDate")
    private Date createdDate;
    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "UpdatedDate", nullable = true)
    private Date updatedDate;
    @OneToMany(targetEntity = Order.class, mappedBy = "shipper", fetch = FetchType.LAZY)
    private List<Order> orderShip;
    @Column(name = "IsActive",nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isActive;
    @Column(name = "IsLocked",nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isLocked;
}
