package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.constant.OrderStatus;
import com.swd391.bachhoasi.model.dto.request.NewOrderRequest;
import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ShipperResponseDto;
import com.swd391.bachhoasi.model.entity.*;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.repository.*;
import com.swd391.bachhoasi.service.OrderService;
import com.swd391.bachhoasi.service.ShipperService;
import com.swd391.bachhoasi.util.AuthUtils;
import com.swd391.bachhoasi.util.TextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final StoreRepository storeRepository;
    private final ProductMenuRepository productMenuRepository;
    private final OrderRepository orderRepository;
    private final OrderContactRepository orderContactRepository;
    private final OrderProductMenuRepository orderProductMenuRepository;
    private final AuthUtils authUtils;
    private final ShipperService shipperService;
    private final ShipperRepository shipperRepository;

    @Override
    public OrderResponse placeOrder(NewOrderRequest order) {
        Store store = storeRepository.findById(order.getStoreId()).orElseThrow(()-> new NotFoundException("Store not found"));
        var loginUser = authUtils.getAdminUserFromAuthentication();
        ShipperResponseDto shipperResponseDto = shipperService.getShipperWithLeastOrders();
        Shipper shipper = shipperRepository.findById(shipperResponseDto.getId()).get();
        // init new order and order contact
        Order newOrder = Order.builder()
                .store(store)
                .orderStatus(OrderStatus.ACCEPTED)
                .payingMethod(order.getPayingMethod())
                .orderDate(order.getDeliveryTime())
                .createdDate(new Date(System.currentTimeMillis()))
                .updatedDate(new Date(System.currentTimeMillis()))
                .admin(loginUser)
                .shipper(shipper)
                .build();
        OrderContact orderContact = OrderContact.builder()
                .buildingNumber(store.getLocation())
                .phoneNumber(store.getPhoneNumber())
                .customerName(store.getName())
                .createdDate(new Date(System.currentTimeMillis()))
                .updatedDate(new Date(System.currentTimeMillis()))
                .build();


        // init order products and set price
        Map<BigDecimal,Integer> orderItems = order.getOrderItems();

        List<BigDecimal> productIds = new ArrayList<>(orderItems.keySet());

        List<ProductMenu> productMenus = productMenuRepository.findBySubIds(productIds);

        List<OrderProductMenu> orderProducts = new ArrayList<>();
        int totalPrice = 0;
        int subTotal = 0;
        for(ProductMenu item : productMenus){
            OrderProductMenu orderProductMenu = OrderProductMenu.builder()
                    .order(newOrder)
                    .product(item)
                    .quantity(orderItems.get(item.getId()))
                    .build();
            orderProducts.add(orderProductMenu);
            subTotal += item.getComposeId().getProduct().getBasePrice().intValue() * orderItems.get(item.getId());
            totalPrice += item.getBasePrice().intValue() *  orderItems.get(item.getId());
        }
        newOrder.setGrandTotal(BigDecimal.valueOf(totalPrice));
        newOrder.setSubTotal(BigDecimal.valueOf(subTotal));

        int point = (int) (totalPrice * 0.1); // 10% of total price
        newOrder.setPoint(point);

        try{
            orderContact = orderContactRepository.save(orderContact);
            newOrder.setOrderContact(orderContact);
            newOrder = orderRepository.save(newOrder);
            orderProductMenuRepository.saveAll(orderProducts);
        }catch (Exception e){
            throw new NotFoundException("Cannot place order");
        }
        return convertOrderToOrderResponse(newOrder);
    }


    public OrderResponse convertOrderToOrderResponse(Order order){
        return OrderResponse.builder()
                .orderId(order.getId())
                .storeName(order.getOrderContact().getCustomerName())
                .orderStatus(order.getOrderStatus())
                .deliveryTime(order.getOrderDate())
                .totalPrice(order.getGrandTotal())
                .storeAddress(order.getOrderContact().getBuildingNumber())
                .orderFeedback(order.getOrderFeedback())
                .deliveryFeedback(order.getDeliveryFeedback())
                .payingMethod(order.getPayingMethod())
                .point(order.getPoint())
                .createdDate(order.getCreatedDate())
                .build();
    }

    @Override
    public PaginationResponse<OrderResponse> getOrders(Pageable pagination, Map<String, String> parameter) {
        if(parameter == null) parameter = new HashMap<>();
        var parameterList = TextUtils.convertKeysToCamel(parameter);
        try {
            Page<OrderResponse> orderPage = orderRepository.searchAnyByParameter(parameterList, pagination)
                    .map(item -> OrderResponse.builder()
                            .orderId(item.getId())
                            .totalPrice(item.getGrandTotal())
                            .orderStatus(item.getOrderStatus())
                            .deliveryFeedback(item.getDeliveryFeedback())
                            .point(item.getPoint())
                            .storeAddress(item.getOrderContact().getBuildingNumber())
                            .orderFeedback(item.getOrderFeedback())
                            .createdDate(item.getCreatedDate())
                            .deliveryFeedback(item.getDeliveryFeedback())
                            .storeName(item.getStore().getName())
                            .payingMethod(item.getPayingMethod())
                            .build());
            return new PaginationResponse<>(orderPage);
        } catch (Exception ex ) {
            throw new ActionFailedException(ex.getMessage(), "ORDER_GET_FAILED");
        }
    }
}