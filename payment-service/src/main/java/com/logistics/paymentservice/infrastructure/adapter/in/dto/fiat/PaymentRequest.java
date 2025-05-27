package com.logistics.paymentservice.infrastructure.adapter.in.dto.fiat;

import java.math.BigDecimal;

public record PaymentRequest (BigDecimal amount, String currency) {}
