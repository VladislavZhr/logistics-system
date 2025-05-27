package com.logistics.routingservice.infrastructure.execeptions.error;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public ErrorResponse(String message, int status, Map<String, String> errors) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
