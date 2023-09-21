package com.shopping.orderService.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.orderService.orderservice.beans.OrderRequest;
import com.shopping.orderService.orderservice.beans.OrderResponse;
import com.shopping.orderService.orderservice.services.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/order")
@Log4j2
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/placeorder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {

        log.info("OrderController | placeOrder is called");

        log.info("OrderController | placeOrder | orderRequest: {}", orderRequest.toString());

        Long orderId = orderService.placeOrder(orderRequest);
        log.info("Order Id: {}", orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {

        log.info("OrderController | getOrderDetails is called");

        OrderResponse orderResponse
                = orderService.getOrderDetails(orderId);

        log.info("OrderController | getOrderDetails | orderResponse : " + orderResponse.toString());

        return new ResponseEntity<>(orderResponse,
                HttpStatus.OK);
    }
}