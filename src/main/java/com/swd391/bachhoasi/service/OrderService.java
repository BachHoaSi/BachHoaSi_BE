package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.OrderRequest;
import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface OrderService
{
    OrderResponse updateOrder(OrderRequest orderRequest);
    PaginationResponse<OrderResponse> getShipperOrders(Pageable pagination, Map<String, String> parameter);

}
