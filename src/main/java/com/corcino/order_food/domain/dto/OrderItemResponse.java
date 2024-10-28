package com.corcino.order_food.domain.dto;

import lombok.Data;

@Data
public class OrderItemResponse {

    private Integer quantity;
    private String description;

}
