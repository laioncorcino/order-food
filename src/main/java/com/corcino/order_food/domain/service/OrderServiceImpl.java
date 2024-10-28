package com.corcino.order_food.domain.service;

import com.corcino.order_food.domain.dto.OrderRequest;
import com.corcino.order_food.domain.dto.OrderResponse;
import com.corcino.order_food.domain.dto.StatusRequest;
import com.corcino.order_food.domain.entity.Order;
import com.corcino.order_food.domain.entity.Status;
import com.corcino.order_food.domain.exception.EntityNotFoundException;
import com.corcino.order_food.domain.interfaces.OrderService;
import com.corcino.order_food.infrastructure.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = mapper.map(orderRequest, Order.class);
        order.setDateTime(LocalDateTime.now());
        order.setStatus(Status.DONE);
        order.getItems().forEach(item -> item.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        return mapper.map(savedOrder, OrderResponse.class);
    }

    @Override
    public OrderResponse updateStatus(StatusRequest statusRequest, Long orderId) {
        Order order = getOrder(orderId);
        Status status = Status.fromString(statusRequest.getStatus());

        order.setStatus(status);
        orderRepository.updateStatus(status, order);
        return mapper.map(order, OrderResponse.class);
    }

    @Override
    public void approvalOrderPayment(Long orderId) {
        Order order = getOrder(orderId);
        order.setStatus(Status.PAID);
        orderRepository.updateStatus(Status.PAID, order);
    }

    @Override
    public Page<OrderResponse> getOrders(Pageable pageable) {
        return orderRepository
                .findAll(pageable)
                .map(o -> mapper.map(o, OrderResponse.class));
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = getOrder(orderId);
        return mapper.map(order, OrderResponse.class);
    }

    public Order getOrder(Long orderId) {
        log.info("Get order with id {}", orderId);
        Optional<Order> order = orderRepository.findById(orderId);

        return order.orElseThrow(() -> {
            log.info("Order with id {} not found", orderId);
            return new EntityNotFoundException(String.format("Order with id %s not found", orderId));
        });
    }

}
