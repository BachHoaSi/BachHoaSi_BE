package com.swd391.bachhoasi.controller;

import com.swd391.bachhoasi.model.dto.request.NewOrderRequest;
import com.swd391.bachhoasi.model.dto.response.ResponseObject;
import com.swd391.bachhoasi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

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


}