package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.NewOrderRequest;
import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;

public interface OrderService {

    public OrderResponse placeOrder(NewOrderRequest order);

}