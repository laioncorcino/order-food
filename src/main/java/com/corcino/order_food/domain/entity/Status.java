package com.corcino.order_food.domain.entity;

public enum Status {

    DONE,
    CANCELLED,

    PAID,
    UNAUTHORIZED,
    CONFIRMED,
    READY,
    OUT_FOR_DELIVERY,
    DELIVERED;

    public static Status fromString(String status) {
        for (Status s : Status.values()) {
            if (s.toString().equals(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }

}
