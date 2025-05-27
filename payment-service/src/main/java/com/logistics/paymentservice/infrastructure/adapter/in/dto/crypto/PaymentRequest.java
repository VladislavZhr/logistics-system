package com.logistics.paymentservice.infrastructure.adapter.in.dto.crypto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private BigDecimal amount;
    private String currency;
}
