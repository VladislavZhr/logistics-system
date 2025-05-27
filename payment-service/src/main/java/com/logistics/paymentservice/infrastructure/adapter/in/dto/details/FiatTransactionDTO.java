package com.logistics.paymentservice.infrastructure.adapter.in.dto.details;

import com.logistics.paymentservice.domain.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FiatTransactionDTO {
    private String userId;
    private String invoiceId;
    private BigDecimal amount;
    private String currency;
    private Date createdAt;
    private PaymentStatus status;
}
