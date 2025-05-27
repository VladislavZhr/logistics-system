package com.logistics.routingservice.infrastructure.execeptions.error;

public class OrderAlreadyDeliveredException extends RuntimeException {
    public OrderAlreadyDeliveredException(String message) {
        super(message);
    }
}
