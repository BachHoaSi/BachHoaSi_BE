package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.swd391.bachhoasi.model.constant.OrderStatus;
import com.swd391.bachhoasi.model.constant.PayingMethod;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class OrderDetailResponse {
    // Base Information
    private BigDecimal orderId;
    private String storeName;
    private OrderStatus orderStatus;
    private BigDecimal total;
    private Date createdAt;
    private Date deliveryTime;
    private String feedback;
    // Destination Info
    private BigDecimal orderContactId;
    private String buildingNumber;
    private String phoneNumber;
    private String street;
    // Order Payment
    private PayingMethod paymentMethod;
    private BigDecimal grandTotal;
    // Product order
    private List<OrderProductMenuResponse> orderProductMenu;
}
