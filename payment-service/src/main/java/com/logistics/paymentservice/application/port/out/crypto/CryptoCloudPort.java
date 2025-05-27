package com.logistics.paymentservice.application.port.out.crypto;

import com.logistics.paymentservice.domain.processed.CryptoPaymentProcessedDetails;

import java.math.BigDecimal;

public interface CryptoCloudPort {
    CryptoPaymentProcessedDetails processCreateLink(String userId, BigDecimal amount, String currency);

}
