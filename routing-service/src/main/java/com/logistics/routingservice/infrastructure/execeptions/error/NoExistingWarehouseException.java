package com.logistics.routingservice.infrastructure.execeptions.error;

public class NoExistingWarehouseException extends RuntimeException {
    public NoExistingWarehouseException(String message) {
        super(message);
    }
}
