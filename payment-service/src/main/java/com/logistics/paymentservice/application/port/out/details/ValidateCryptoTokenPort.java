package com.logistics.paymentservice.application.port.out.details;

public interface ValidateCryptoTokenPort {
    void validateToken(String token, String invoiceId);
}
