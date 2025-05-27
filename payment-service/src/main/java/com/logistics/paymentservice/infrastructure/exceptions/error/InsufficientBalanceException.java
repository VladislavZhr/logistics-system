package com.logistics.paymentservice.infrastructure.exceptions.error;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
