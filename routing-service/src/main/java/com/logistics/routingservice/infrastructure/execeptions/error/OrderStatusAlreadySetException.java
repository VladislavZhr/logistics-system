package com.logistics.routingservice.infrastructure.execeptions.error;

public class OrderStatusAlreadySetException extends RuntimeException {
    public OrderStatusAlreadySetException(String message) {
        super(message);
    }
}
