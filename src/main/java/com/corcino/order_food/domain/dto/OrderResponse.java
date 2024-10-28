package com.corcino.order_food.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {

    private Long orderId;
    private LocalDateTime dateTime;
    private String status;
    private List<OrderItemResponse> items = new ArrayList<>();

}
