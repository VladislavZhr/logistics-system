package com.logistics.paymentservice.infrastructure.exceptions.error;

public class NoFoundUserIdException extends RuntimeException {
    public NoFoundUserIdException(String message) {
        super(message);
    }
}
