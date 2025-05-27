package com.logistics.paymentservice.application.port.in;

import com.logistics.paymentservice.domain.model.PaymentStatus;

import java.math.BigDecimal;

public interface CryptoPaymentUseCase {
    String createPayment(String userId, BigDecimal amount, String currency);
    void handlePayment(PaymentStatus status, String invoiceId, String cryptoCurrency);
    void validateToken(String token, String invoiceId);
}
