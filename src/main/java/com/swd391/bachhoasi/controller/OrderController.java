package com.swd391.bachhoasi.controller;

import com.swd391.bachhoasi.model.constant.OrderStatus;
import com.swd391.bachhoasi.model.dto.request.NewOrderRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.OrderDetailResponse;
import com.swd391.bachhoasi.model.dto.response.OrderResponse;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Add order", description = "Add order by store id, cart id and payment method: COD, BANKING; Date time format: yyyy-MM-ddTHH:mm:ss")
    @PostMapping
    public ResponseEntity<ResponseObject> addOrder(@RequestBody @Valid NewOrderRequest orderRequest, BindingResult bindingResult) {

        var data = orderService.placeOrder(orderRequest);
        var responseObject = ResponseObject.builder()
                .code("ORDER_ADD_SUCCESS")
                .message("Add order successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .data(data)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getOrders(
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pagination,
            @RequestParam(required = false, name = "q") String query
    ) {
        var queryDto = SearchRequestParamsDto.builder()
                .search(query)
                .wrapSort(pagination)
                .build();
        var result = orderService.getOrders(queryDto);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .code("ORDER_GET_SUCCESS")
                        .isSuccess(true)
                        .data(result)
                        .message("Get Order Success")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PatchMapping
    public ResponseEntity<ResponseObject> acceptOrder(@RequestParam BigDecimal id) {
        OrderResponse orderResponse = orderService.acceptOrder(id);
        var responseObject = ResponseObject.builder()
                .data(orderResponse)
                .code("ORDER_ACCEPT_SUCCESS")
                .message("Disable product successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok().body(responseObject);

    }
    @GetMapping("{orderId}")
    public ResponseEntity<ResponseObject> getOrderDetail(@PathVariable(name = "orderId") BigDecimal orderId) {
        OrderDetailResponse orderResponse = orderService.getDetailOrder(orderId);
        var responseObject = ResponseObject.builder()
                .data(orderResponse)
                .code("GET_ORDER_DETAILED_SUCCESS")
                .message("Get order detailed successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }

    @PatchMapping("cancel/{orderId}")
    public ResponseEntity<ResponseObject> cancelOrder(@PathVariable(name = "orderId") BigDecimal orderId) {
        OrderResponse orderResponse = orderService.cancelOrder(orderId);
        var responseObject = ResponseObject.builder()
                .data(orderResponse)
                .code("CANCEL_ORDER_SUCCESS")
                .message("Cancel order successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }

    @PatchMapping("change-status/{id}")
    public ResponseEntity<ResponseObject> changeStatus (@PathVariable(name = "id") BigDecimal id, @RequestParam(name = "status") OrderStatus status) {
        OrderResponse orderResponse = orderService.changeOrderStatus(id, status);
        var responseObject = ResponseObject.builder()
                .data(orderResponse)
                .code("CHANGE_ORDER_STATUS_SUCCESS")
                .message("Change order status successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }
}