package com.logistics.paymentservice.infrastructure.adapter.out.database.mapper;

import com.logistics.paymentservice.domain.model.CryptoTransaction;
import com.logistics.paymentservice.infrastructure.adapter.out.database.entity.CryptoTransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class CryptoMapper {

    public static CryptoTransactionEntity toCryptoEntity(CryptoTransaction cryptoTransaction) {
        return CryptoTransactionEntity.builder()
                .orderId(cryptoTransaction.getOrderId())
                .userId(cryptoTransaction.getUserId())
                .invoiceId(cryptoTransaction.getInvoiceId())
                .amount(cryptoTransaction.getAmount())
                .currency(cryptoTransaction.getCurrency())
                .cryptocurrency(cryptoTransaction.getCryptocurrency())
                .createdAt(cryptoTransaction.getCreatedAt())
                .status(cryptoTransaction.getStatus())
                .build();
    }

    public static CryptoTransaction toCryptoDomain(CryptoTransactionEntity cryptoTransactionEntity){
        return CryptoTransaction.builder()
                .orderId(cryptoTransactionEntity.getOrderId())
                .userId(cryptoTransactionEntity.getUserId())
                .invoiceId(cryptoTransactionEntity.getInvoiceId())
                .amount(cryptoTransactionEntity.getAmount())
                .currency(cryptoTransactionEntity.getCurrency())
                .cryptocurrency(cryptoTransactionEntity.getCryptocurrency())
                .createdAt(cryptoTransactionEntity.getCreatedAt())
                .status(cryptoTransactionEntity.getStatus())
                .build();
    }
}
