package com.swd391.bachhoasi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swd391.bachhoasi.model.constant.OrderStatus;
import com.swd391.bachhoasi.model.constant.PayingMethod;
import com.swd391.bachhoasi.model.entity.Shipper;
import com.swd391.bachhoasi.model.entity.Store;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderRequest {
    private BigDecimal id;
    @NotBlank(message = "FeedBack should't empty")
    private String deliveryFeedback;
    @NotBlank(message = "Order Status should't empty")
    private OrderStatus orderStatus;
    @JsonProperty("shipper-id")
    private BigDecimal shipperId;
    @NotBlank(message = "PayingMethod should't empty")
    private PayingMethod payingMethod;
    private Double discount;
    @JsonProperty("store-id")
    private BigDecimal store_Id;
    @JsonProperty("contact-id")
    private BigDecimal orderContact_Id;
    private BigDecimal subTotal;
}
