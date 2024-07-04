package com.swd391.bachhoasi.model.dto.response;

import com.swd391.bachhoasi.model.constant.OrderStatus;
import com.swd391.bachhoasi.model.constant.PayingMethod;
import com.swd391.bachhoasi.model.entity.OrderContact;
import com.swd391.bachhoasi.model.entity.Shipper;
import com.swd391.bachhoasi.model.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private BigDecimal id;
    private String deliveryFeedback;
    private OrderStatus orderStatus;
    private OrderContact orderContact;
    private String shipperName;
    private BigDecimal grandTotal;
    private String storeName;
    private PayingMethod payingMethod;

}
