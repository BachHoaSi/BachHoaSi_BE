package com.swd391.bachhoasi.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swd391.bachhoasi.model.constant.OrderStatus;
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
}
