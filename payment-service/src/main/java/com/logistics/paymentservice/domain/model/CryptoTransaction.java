package com.logistics.paymentservice.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Builder
public class CryptoTransaction {
    private Long orderId;
    private String userId;
    private String invoiceId;
    private BigDecimal amount;
    private String currency;
    private String cryptocurrency;
    private Date createdAt;
    private PaymentStatus status;
}
