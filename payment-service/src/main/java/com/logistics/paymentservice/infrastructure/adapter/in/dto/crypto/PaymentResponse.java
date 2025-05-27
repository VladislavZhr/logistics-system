package com.logistics.paymentservice.infrastructure.adapter.in.dto.crypto;

import com.logistics.paymentservice.domain.model.PaymentStatus;

public record PaymentResponse(PaymentStatus status, String invoice_id, String currency, String token) {
}
