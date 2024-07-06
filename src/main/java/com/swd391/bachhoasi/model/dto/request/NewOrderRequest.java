package com.swd391.bachhoasi.model.dto.request;

import com.swd391.bachhoasi.model.constant.PayingMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Validated

public class NewOrderRequest {
    @NotNull(message = "Store id should not be null")
    @Schema(description = "ID of the store", required = true, example = "5678.90")
    private BigDecimal storeId;

    @NotNull(message = "The list of order items should not be null")
    @Schema(description = "List of order items ID:Quantity", required = true)
    private Map<BigDecimal,Integer> orderItems;
    @NotNull(message = "Payment method should not be null")
    @Schema(description = "Payment method for the order", required = true)
    private PayingMethod payingMethod;
    @NotNull(message = "Delivery time is required")
    @Schema(description = "Time want to delivery!")
    private LocalDateTime deliveryTime;

}