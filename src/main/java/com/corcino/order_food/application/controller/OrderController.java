package com.corcino.order_food.application.controller;

import com.corcino.order_food.domain.dto.OrderRequest;
import com.corcino.order_food.domain.dto.OrderResponse;
import com.corcino.order_food.domain.dto.StatusRequest;
import com.corcino.order_food.domain.interfaces.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public ResponseEntity<Page<OrderResponse>> getOrders(@PageableDefault(sort = "orderId", direction = ASC) Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrders(pageable));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long orderId) {
        return  ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping()
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest, UriComponentsBuilder uriBuilder) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        URI path = uriBuilder.path("/api/v1/order/{orderId}").buildAndExpand(orderResponse.getOrderId()).toUri();
        return ResponseEntity.created(path).body(orderResponse);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long orderId, @RequestBody StatusRequest status){
        return ResponseEntity.ok(orderService.updateStatus(status, orderId));
    }


    @PutMapping("/{orderId}/paid")
    public ResponseEntity<Void> approvePayment(@PathVariable Long orderId) {
        orderService.approvalOrderPayment(orderId);
        return ResponseEntity.ok().build();
    }

}
