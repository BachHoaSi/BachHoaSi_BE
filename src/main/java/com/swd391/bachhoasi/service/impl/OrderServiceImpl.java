package com.swd391.bachhoasi.service.impl;


import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.entity.Order;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.repository.*;
import com.swd391.bachhoasi.service.OrderService;
import com.swd391.bachhoasi.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.swd391.bachhoasi.util.AuthUtils;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShipperRepository shipperRepository;
    private final OrderContactRepository orderContactRepository;
    private final StoreRepository storeRepository;
    private final AuthUtils authUtils;

    @Override
    public PaginationResponse<OrderResponse> getOrders(Pageable pagination, Map<String, String> parameter) {
        if(parameter == null) parameter = new HashMap<>();
        var parameterList = TextUtils.convertKeysToCamel(parameter);
        try {
            Page<OrderResponse> orderPage = orderRepository.searchAnyByParameter(parameterList, pagination)
                    .map(item -> OrderResponse.builder()
                            .id(item.getId())
                            .orderContact(item.getOrderContact())
                            .orderStatus(item.getOrderStatus())
                            .deliveryFeedback(item.getDeliveryFeedback())
                            .grandTotal(item.getGrandTotal())
                            .shipperName(item.getShipper().getName())
                            .storeName(item.getStore().getName())
                            .payingMethod(item.getPayingMethod())
                            .build());
            return new PaginationResponse<>(orderPage);
        } catch (Exception ex ) {
            throw new ActionFailedException(ex.getMessage(), "ORDER_GET_FAILED");
        }
    }

    /*@Override
    public OrderResponse updateOrder(OrderRequest orderRequest)
    {
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

        List<OrderProductMenu> list = new ArrayList<>();
        list = orderEntity.getOrderProducts();
        orderEntity.setOrderStatus(orderRequest.getOrderStatus());
        orderEntity.setUpdatedDate(new Date(System.currentTimeMillis()));
        orderEntity.setShipper(shipperRepository.findById(orderRequest.getShipperId()).get());
        orderEntity.setOrderFeedback(orderRequest.getDeliveryFeedback());
        orderEntity.setDiscount(orderRequest.getDiscount());
        BigDecimal firstTotal = (BigDecimal.valueOf(orderRequest.getDiscount()));
        BigDecimal grandTotal = calculateGrandTotal(list).multiply(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(orderRequest.getDiscount()))).add(orderRequest.getSubTotal());
        orderEntity.setGrandTotal(grandTotal);

        try {
            Order updatedOrder = orderRepository.save(orderEntity);
            return mapToOrderRespone(updatedOrder);
        } catch (Exception e) {
            throw new ValidationFailedException("Cannot update Order, please check again !!!");
        }
    }

    @Override
    public OrderResponse deleteOrderById(BigDecimal id) {
        if(id.intValue() < 0)
            throw new ValidationFailedException("Order id isn't valid, please check again !!!");
        Order deletedObject = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Not found store level with id: %s", id.toString()))
        );
        try {
            orderRepository.delete(deletedObject);
        }catch(Exception ex) {
            throw new ActionFailedException("This Order is used by others, please remove them before this");
        }

        return OrderResponse.builder()
                .id(deletedObject.getId())

                .build();
    }

    @Override
    public OrderResponse CreateOrder(OrderRequest orderRequest, List<OrderProductMenu> orderProducts ) {
        var loginUser = authUtils.getAdminUserFromAuthentication();
        if(orderRequest == null) throw new ValidationFailedException("Order request is null, please check again !!!");
        BigDecimal firstTotal = calculateGrandTotal(orderProducts);
        BigDecimal grandTotal = firstTotal.multiply(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(orderRequest.getDiscount()))).add(orderRequest.getSubTotal());
        Integer point = grandTotal.multiply(BigDecimal.valueOf(0.0001)).intValue();
        var orderEntity = Order.builder()
                .orderStatus(orderRequest.getOrderStatus())
                .createdDate(new Date(System.currentTimeMillis()))
                .payingMethod(orderRequest.getPayingMethod())
                .discount(orderRequest.getDiscount())
                .admin(loginUser)
                .orderProducts(orderProducts)
                .point(point)
                .grandTotal(grandTotal)
                .subTotal(orderRequest.getSubTotal())
                .orderContact(orderContactRepository.findById(orderRequest.getOrderContact_Id()).get())
                .store(storeRepository.findById(orderRequest.getStore_Id()).get())
                .shipper(shipperRepository.findById(orderRequest.getShipperId()).get())
                .build();
        try {
            orderRepository.save(orderEntity);
            return OrderResponse.builder()
                    .id(orderEntity.getId())
                    .shipperName(orderEntity.getShipper().getName())
                    .orderStatus(orderEntity.getOrderStatus())
                    .grandTotal(orderEntity.getGrandTotal())
                    .storeName(orderEntity.getStore().getName())
                    .payingMethod(orderEntity.getPayingMethod())
                    .build();
        }catch(ConstraintViolationException cve){
            throw new ActionFailedException(cve.getMessage(), "ORDER_DUPLICATE");
        }catch(Exception ex) {
            throw new ActionFailedException(ex.getMessage(), "SAVE_ORDER_FAILED");
        }
    }

    private BigDecimal calculateGrandTotal(List<OrderProductMenu> orderProducts) {
        BigDecimal grandTotal = BigDecimal.ZERO;
        for (OrderProductMenu orderProduct : orderProducts) {
            BigDecimal productTotal = orderProduct.getProduct().getBasePrice().multiply(BigDecimal.valueOf(orderProduct.getQuantity()));
            grandTotal = grandTotal.add(productTotal);
        }
        return grandTotal;
    }
*/

    public OrderResponse mapToOrderRespone(Order order) {
        return OrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .build();
    }

}
