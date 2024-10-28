package com.corcino.order_food.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @NotNull
    @Positive
    private Integer quantity;

    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

}
