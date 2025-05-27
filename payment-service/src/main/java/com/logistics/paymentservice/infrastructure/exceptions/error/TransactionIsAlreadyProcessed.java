package com.logistics.paymentservice.infrastructure.exceptions.error;

public class TransactionIsAlreadyProcessed extends RuntimeException {
    public TransactionIsAlreadyProcessed(String message) {
        super(message);
    }
}
