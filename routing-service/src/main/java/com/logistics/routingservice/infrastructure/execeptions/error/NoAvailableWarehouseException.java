package com.logistics.routingservice.infrastructure.execeptions.error;

public class NoAvailableWarehouseException extends RuntimeException {
    public NoAvailableWarehouseException(String message) {
        super(message);
    }
}
