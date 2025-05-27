package com.logistics.paymentservice.application.port.out.crypto;

import com.logistics.paymentservice.domain.model.CryptoTransaction;
import com.logistics.paymentservice.domain.model.UserBalance;

import java.util.List;
import java.util.Optional;

public interface CryptoTransactionPort {
    void save(CryptoTransaction cryptoTransaction);
    Optional<CryptoTransaction> findByInvoiceId(String InvoiceId);
    List<CryptoTransaction> findTransactionByUserId(String userId);
    List<CryptoTransaction> findAllTransaction();

}
