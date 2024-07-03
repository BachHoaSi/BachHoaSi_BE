package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.constant.OrderStatus;
import com.swd391.bachhoasi.model.dto.request.OrderRequest;
import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.entity.Order;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.repository.OrderRepository;
import com.swd391.bachhoasi.service.OrderService;
import com.swd391.bachhoasi.util.AuthUtils;
import com.swd391.bachhoasi.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AuthUtils authUtils;
    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest)
    {
        var loginUser = authUtils.getShipper();
        if (loginUser.getId() == orderRequest.getShipperId()){
            throw new ValidationFailedException("The order is delivered to another shipper, please check again !!!");
        }

        if (orderRequest == null) {
            throw new ValidationFailedException("Order request is null, please check again !!!");
        }
        Optional<Order> orderOptional = orderRepository.findById(orderRequest.getId());
        if (orderOptional.isEmpty()) {
            throw new ValidationFailedException("Order not found, please check again !!!");
        }
        Order orderEntity = orderOptional.get();
        if (!orderEntity.getOrderStatus().equals(OrderStatus.PENDING)) {
            throw new ValidationFailedException("Order is " + orderEntity.getOrderStatus().toString() + ", cannot change shipper!!!");
        }
        orderEntity.setOrderStatus(orderRequest.getOrderStatus());
        orderEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        orderEntity.setShipper(loginUser);
        orderEntity.setOrderFeedback(orderRequest.getDeliveryFeedback());

        try {
            Order updatedOrder = orderRepository.save(orderEntity);
            return mapToOrderRespone(updatedOrder);
        } catch (Exception e) {
            throw new ValidationFailedException("Cannot update Order, please check again !!!");
        }
    }



    @Override
    public PaginationResponse<OrderResponse> getShipperOrders(Pageable pagination, Map<String, String> parameter) {
        var loginUser = authUtils.getShipper();
        if(parameter == null) parameter = new HashMap<>();
        var parameterList = TextUtils.convertKeysToCamel(parameter);
        try {
            Page<OrderResponse> orderPage = orderRepository.searchByParameterAndShipperId(parameterList, pagination, loginUser.getId())
                    .map(item -> OrderResponse.builder()
                            .id(item.getId())
                            .orderContact(item.getOrderContact())
                            .orderStatus(item.getOrderStatus())
                            .deliveryFeedback(item.getDeliveryFeedback())
                            .build());
            return new PaginationResponse<>(orderPage);
        } catch (Exception ex ) {
            throw new ActionFailedException(ex.getMessage(), "ORDER_GET_FAILED");
        }
    }

    public OrderResponse mapToOrderRespone(Order order) {
        return OrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
