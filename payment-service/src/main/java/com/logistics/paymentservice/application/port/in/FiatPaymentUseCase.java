package com.logistics.paymentservice.application.port.in;

import com.logistics.paymentservice.domain.model.PaymentStatus;

import java.math.BigDecimal;

public interface FiatPaymentUseCase {
    String createPayment(String userId, BigDecimal amount, String currency);
    void handlePayment(String payload);
    void validatePayment(String sigHeader, String payload);
}
