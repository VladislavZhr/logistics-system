package com.logistics.routingservice.infrastructure.execeptions.error;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
