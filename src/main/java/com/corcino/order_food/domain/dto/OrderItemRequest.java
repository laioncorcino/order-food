package com.corcino.order_food.domain.dto;

import lombok.Data;

@Data
public class OrderItemRequest {

    private Integer quantity;
    private String description;

}
