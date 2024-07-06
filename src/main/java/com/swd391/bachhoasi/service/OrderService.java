package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.NewOrderRequest;
import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OrderService {

    public OrderResponse placeOrder(NewOrderRequest order);
    PaginationResponse<OrderResponse> getOrders(Pageable pagination, Map<String, String> parameter);
}