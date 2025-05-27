package com.logistics.paymentservice.infrastructure.exceptions.error;

public class StripePaymentException extends RuntimeException {
    public StripePaymentException(String message) {
        super(message);
    }
}
