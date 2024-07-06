package com.swd391.bachhoasi.model.dto.response;

import com.swd391.bachhoasi.model.constant.OrderStatus;
import com.swd391.bachhoasi.model.constant.PayingMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response representing an order with its details")
public class OrderResponse {

    @Schema(description = "ID of the order", example = "1234.56")
    private BigDecimal orderId;

    @Schema(description = "Total price of the order", example = "125.75")
    private BigDecimal totalPrice;

    @Schema(description = "Payment method used for the order")
    private PayingMethod payingMethod;

    @Schema(description = "Time when the order will be delivered", example = "2021-12-31T23:59:59.999999999")
    private LocalDateTime deliveryTime;

    @Schema(description = "Status of the order")
    private OrderStatus orderStatus;

    @Schema(description = "Points earned or used for the order", example = "100")
    private Integer point;

    @Schema(description = "Name of the store where the order was placed")
    private String storeName;

    @Schema(description = "Address of the store where the order was placed")
    private String storeAddress;

    @Schema(description = "Feedback provided for the order")
    private String orderFeedback;

    @Schema(description = "Feedback provided for the delivery of the order")
    private String deliveryFeedback;

    @Schema(description = "Date when the order was created")
    private Date createdDate;
}