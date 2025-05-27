package com.logistics.paymentservice.application.port.out.fiat;

import com.logistics.paymentservice.domain.model.FiatTransaction;

import java.util.List;
import java.util.Optional;

public interface FiatTransactionPort {
    void save(FiatTransaction fiatTransaction);
    Optional<FiatTransaction> findByInvoiceId(String invoiceId);
    List<FiatTransaction> findTransactionByUserId(String userId);
    List<FiatTransaction> getAllFiatTransactions();


}
