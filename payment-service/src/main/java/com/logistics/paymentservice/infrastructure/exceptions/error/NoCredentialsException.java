package com.logistics.paymentservice.infrastructure.exceptions.error;

public class NoCredentialsException extends RuntimeException {
    public NoCredentialsException(String message) {
        super(message);
    }
}
