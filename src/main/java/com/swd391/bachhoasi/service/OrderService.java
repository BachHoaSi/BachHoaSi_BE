package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.OrderRequest;
import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.StoreTypeResponse;
import com.swd391.bachhoasi.model.entity.OrderProductMenu;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService {
    PaginationResponse<OrderResponse> getOrders(Pageable pagination, Map<String, String> parameter);
}
