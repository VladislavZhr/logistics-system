package com.logistics.paymentservice.infrastructure.adapter.out.database.mapper;

import com.logistics.paymentservice.domain.model.FiatTransaction;
import com.logistics.paymentservice.infrastructure.adapter.out.database.entity.FiatTransactionEntity;

public class FiatMapper {

    public static FiatTransactionEntity toEntity(FiatTransaction transaction) {
        return FiatTransactionEntity.builder()
                .userId(transaction.getUserId())
                .invoiceId(transaction.getInvoiceId())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .createdAt(transaction.getCreatedAt())
                .status(transaction.getStatus())
                .build();
    }

    public static FiatTransaction toDomain(FiatTransactionEntity entity) {
        return FiatTransaction.builder()
                .orderId(entity.getOrderId())
                .userId(entity.getUserId())
                .invoiceId(entity.getInvoiceId())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .createdAt(entity.getCreatedAt())
                .status(entity.getStatus())
                .build();
    }
}
