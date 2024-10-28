package com.corcino.order_food.domain.interfaces;

import com.corcino.order_food.domain.dto.OrderRequest;
import com.corcino.order_food.domain.dto.OrderResponse;
import com.corcino.order_food.domain.dto.StatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<OrderResponse> getOrders(Pageable pageable);
    OrderResponse getOrderById(Long orderId);
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse updateStatus(StatusRequest statusRequest, Long orderId);
    void approvalOrderPayment(Long orderId);

}
