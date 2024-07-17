package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.NewOrderRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.OrderDetailResponse;
import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;

import java.math.BigDecimal;

public interface OrderService {

    public OrderResponse placeOrder(NewOrderRequest order);
    PaginationResponse<OrderResponse> getOrders(SearchRequestParamsDto request);
    OrderDetailResponse getDetailOrder(BigDecimal orderId);
    public OrderResponse acceptOrder(BigDecimal orderId);
    OrderResponse cancelOrder(BigDecimal orderId);
}