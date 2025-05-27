package com.logistics.paymentservice.application.port.out.fiat;

import com.logistics.paymentservice.domain.processed.FiatHandleProcessedDetails;
import com.logistics.paymentservice.domain.processed.FiatPaymentProcessedDetails;

import java.math.BigDecimal;

public interface StripePort {
    FiatPaymentProcessedDetails processCreateLink(String userId, String currency, BigDecimal amount);
    FiatHandleProcessedDetails processHandlePayment(String payload);
    void validatePayment(String sigHeader, String payload);
}
