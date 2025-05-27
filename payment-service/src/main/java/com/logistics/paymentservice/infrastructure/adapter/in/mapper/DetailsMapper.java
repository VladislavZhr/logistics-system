package com.logistics.paymentservice.infrastructure.adapter.in.mapper;

import com.logistics.paymentservice.domain.model.CryptoTransaction;
import com.logistics.paymentservice.domain.model.FiatTransaction;
import com.logistics.paymentservice.infrastructure.adapter.in.dto.details.CryptoTransactionDTO;
import com.logistics.paymentservice.infrastructure.adapter.in.dto.details.FiatTransactionDTO;

public class DetailsMapper {

    public static CryptoTransactionDTO forUsersCryptoTransactionDTO(CryptoTransaction cryptoTransaction) {
        return CryptoTransactionDTO.builder()
                .invoiceId(cryptoTransaction.getInvoiceId())
                .amount(cryptoTransaction.getAmount())
                .currency(cryptoTransaction.getCurrency())
                .cryptocurrency(cryptoTransaction.getCryptocurrency())
                .createdAt(cryptoTransaction.getCreatedAt())
                .status(cryptoTransaction.getStatus())
                .build();
    }

    public static CryptoTransactionDTO forAllCryptoTransactionDTO(CryptoTransaction cryptoTransaction) {
        return CryptoTransactionDTO.builder()
                .userId(cryptoTransaction.getUserId())
                .invoiceId(cryptoTransaction.getInvoiceId())
                .amount(cryptoTransaction.getAmount())
                .currency(cryptoTransaction.getCurrency())
                .cryptocurrency(cryptoTransaction.getCryptocurrency())
                .createdAt(cryptoTransaction.getCreatedAt())
                .status(cryptoTransaction.getStatus())
                .build();
    }

    public static FiatTransactionDTO forUsersFiatTransactionDTO(FiatTransaction fiatTransaction) {
        return FiatTransactionDTO.builder()
                .invoiceId(fiatTransaction.getInvoiceId())
                .amount(fiatTransaction.getAmount())
                .currency(fiatTransaction.getCurrency())
                .createdAt(fiatTransaction.getCreatedAt())
                .status(fiatTransaction.getStatus())
                .build();
    }

    public static FiatTransactionDTO forAllFiatTransactionDTO(FiatTransaction fiatTransaction) {
        return FiatTransactionDTO.builder()
                .userId(fiatTransaction.getUserId())
                .invoiceId(fiatTransaction.getInvoiceId())
                .amount(fiatTransaction.getAmount())
                .currency(fiatTransaction.getCurrency())
                .createdAt(fiatTransaction.getCreatedAt())
                .status(fiatTransaction.getStatus())
                .build();
    }



}
