package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.constant.OrderStatus;
import com.swd391.bachhoasi.model.dto.request.NewOrderRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.OrderDetailResponse;
import com.swd391.bachhoasi.model.dto.response.OrderProductMenuResponse;
import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ShipperResponseDto;
import com.swd391.bachhoasi.model.entity.*;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.repository.*;
import com.swd391.bachhoasi.service.OrderService;
import com.swd391.bachhoasi.service.ShipperService;
import com.swd391.bachhoasi.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private final ProductRepository productRepository;
    @Override
    public OrderResponse placeOrder(NewOrderRequest order) {
        Store store = storeRepository.findById(order.getStoreId()).orElseThrow(()-> new NotFoundException("Store not found"));
        var loginUser = authUtils.getAdminUserFromAuthentication();
        ShipperResponseDto shipperResponseDto = shipperService.getShipperWithLeastOrders();
        Shipper shipper = shipperRepository.findById(shipperResponseDto.getId()).get();
        // init new order and order contact
        Order newOrder = Order.builder()
                .store(store)
                .orderStatus(OrderStatus.PICKED_UP)
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
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal subTotal = BigDecimal.ZERO;
        for(Map.Entry<BigDecimal,Integer> entry : orderItems.entrySet()){
            BigDecimal productId = entry.getKey();
            if (!productRepository.findById(productId).get().getStatus().booleanValue()){
                throw new ActionFailedException(productRepository.findById(productId).get().getName() + " not available");
            }
            if (productRepository.findById(productId).get().getStockQuantity() < entry.getValue()){
                throw new ActionFailedException(productRepository.findById(productId).get().getName() + " is not enough");
            }
        }
        for(ProductMenu item : productMenus){
            OrderProductMenu orderProductMenu = OrderProductMenu.builder()
                    .order(newOrder)
                    .product(item)
                    .quantity(orderItems.get(item.getId()))
                    .build();
            orderProducts.add(orderProductMenu);
            int intValue = orderItems.get(item.getId()); // example integer value
            BigDecimal bigDecimalValue = new BigDecimal(intValue);
            totalPrice = totalPrice.add(item.getBasePrice().multiply(bigDecimalValue));
            subTotal = subTotal.add(item.getComposeId().getProduct().getBasePrice().multiply(bigDecimalValue));

        }
        newOrder.setGrandTotal(totalPrice);
        newOrder.setSubTotal(subTotal);

        int point = (int) (totalPrice.intValue() * 0.1); // 10% of total price
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
    public PaginationResponse<OrderResponse> getOrders(SearchRequestParamsDto request) {

        try {
            Page<OrderResponse> orderPage = orderRepository.searchAnyByParameter(request.search(), request.pagination())
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

    public OrderDetailResponse getDetailOrder(BigDecimal orderId) {
        var orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Not found this order"));
        var orderContact = orderEntity.getOrderContact();
        var orderProductList = orderEntity.getOrderProducts();
        List<OrderProductMenuResponse> orderProductListResponse = Collections.emptyList();
        if (orderProductList != null) {
            orderProductListResponse = orderProductList.stream().map(item -> {
                var productMenu = item.getProduct();
                var product = productMenu.getComposeId().getProduct();
                return OrderProductMenuResponse.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .productName(product.getName())
                .url(product.getUrlImages())
                .category(product.getCategory().getName())
                .price(productMenu.getBasePrice())
                .build();
            }).toList();
        }
        return OrderDetailResponse.builder()
            .orderId(orderId)
            .storeName(orderContact.getCustomerName())
            .orderStatus(orderEntity.getOrderStatus())
            .total(orderEntity.getSubTotal())
            .createdAt(orderEntity.getCreatedDate())
            .deliveryTime(orderEntity.getOrderDate())
            .feedback(orderEntity.getOrderFeedback())
            .orderContactId(orderContact.getId())
            .buildingNumber(orderContact.getBuildingNumber())
            .phoneNumber(orderContact.getPhoneNumber())
            .street(orderContact.getStreet())
            .paymentMethod(orderEntity.getPayingMethod())
            .grandTotal(orderEntity.getGrandTotal())
            .orderProductMenu(orderProductListResponse)
        .build();
    }

    public OrderResponse cancelOrder(BigDecimal orderId) {
        var orderResponse = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Not found order with this ID"));
        var orderStatus = orderResponse.getOrderStatus();
        var acceptStatus = List.of(OrderStatus.ACCEPTED, OrderStatus.IN_TRANSIT, OrderStatus.PENDING, OrderStatus.PICKED_UP);
        if (acceptStatus.contains(orderStatus)) {
            orderResponse.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(orderResponse);
        } else {
            throw new ValidationFailedException(String.format("Order can accepts when meet condition: %s", acceptStatus.toString()));
        }
        return convertOrderToOrderResponse(orderResponse);
    }
    @Override
    public OrderResponse acceptOrder(BigDecimal orderId) {
        var order = orderRepository.findById(orderId);
        if (order.isEmpty()) throw new NotFoundException("Order not found");
        if (order.get().getOrderStatus() != OrderStatus.PICKED_UP)
            throw new ActionFailedException("Cannot pickup this order");
        List<OrderProductMenu> orderProductMenuList = orderProductMenuRepository.findByOrderId(orderId);
        if (orderProductMenuList.isEmpty())
            throw new NotFoundException("Cannot find order product");
        Map<BigDecimal,Integer> orderItems = convertToMap(orderProductMenuList);
        List<BigDecimal> productIds = new ArrayList<>(orderItems.keySet());
        List<BigDecimal> missingIds = new ArrayList<>(orderItems.keySet());
        for(BigDecimal productId : productIds){
            if (productRepository.existsById(productId)){
                missingIds.remove(productId);
            }
        }
        if (!missingIds.isEmpty()) {
            throw new IllegalArgumentException("The following IDs do not exist: " + missingIds);
        }
        for(Map.Entry<BigDecimal,Integer> entry : orderItems.entrySet()){
            BigDecimal productId = entry.getKey();
            Integer quantity = entry.getValue();
            Product productEntity = productRepository.findById(productId).get();
            if (!productEntity.getStatus().booleanValue()){
                throw new ActionFailedException(productEntity.getName() + "is disabled");
            }
            if (productEntity.getStockQuantity() < entry.getValue()){
                throw new ActionFailedException(productEntity.getName() + " is not enough");
            }
            var product = productRepository.findById(productId).get();
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.save(product);
        }
        order.get().setOrderStatus(OrderStatus.ACCEPTED);
        orderRepository.save(order.get());

        return convertOrderToOrderResponse(order.get());
    }

    public static Map<BigDecimal, Integer> convertToMap(List<OrderProductMenu> orderProductMenus) {
        Map<BigDecimal, Integer> orderItems = new HashMap<>();
        for (OrderProductMenu opm : orderProductMenus) {
            orderItems.put(opm.getProduct().getComposeId().getProduct().getId(), opm.getQuantity());
        }
        return orderItems;
    }
}