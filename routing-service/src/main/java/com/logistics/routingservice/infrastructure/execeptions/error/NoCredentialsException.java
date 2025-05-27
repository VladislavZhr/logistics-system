package com.logistics.routingservice.infrastructure.execeptions.error;

public class NoCredentialsException extends RuntimeException {
    public NoCredentialsException(String message) {
        super(message);
    }
}
