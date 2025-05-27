package com.logistics.paymentservice.infrastructure.exceptions.error;

public class NoFoundInvoiceIdException extends RuntimeException {
    public NoFoundInvoiceIdException(String message) {
        super(message);
    }
}
