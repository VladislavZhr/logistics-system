package com.logistics.paymentservice.application.port.in;

import com.logistics.paymentservice.domain.model.CryptoTransaction;
import com.logistics.paymentservice.domain.model.FiatTransaction;
import com.logistics.paymentservice.domain.model.UserBalance;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentsDetailsUseCase {
    List<UserBalance> getAllUserBalance();
    BigDecimal getBalance(String userId);
    List<CryptoTransaction> getCryptoTransactions(String userId);
    List<CryptoTransaction> getAllCryptoTransactions();
    List<FiatTransaction> getFiatTransactions(String userId);
    List<FiatTransaction> getAllFiatTransactions();
    void payForDelivery(String userId, BigDecimal amount);
}
