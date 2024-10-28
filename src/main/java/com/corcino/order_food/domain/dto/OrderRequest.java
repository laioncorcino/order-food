package com.corcino.order_food.domain.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequest {

    private List<OrderItemRequest> items = new ArrayList<>();

}
