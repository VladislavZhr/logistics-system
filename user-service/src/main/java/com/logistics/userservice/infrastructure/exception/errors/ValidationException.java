package com.logistics.userservice.infrastructure.exception.errors;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
