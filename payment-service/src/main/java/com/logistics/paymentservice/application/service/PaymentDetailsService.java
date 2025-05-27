package com.logistics.paymentservice.application.service;

import com.logistics.paymentservice.application.port.in.PaymentsDetailsUseCase;
import com.logistics.paymentservice.application.port.out.crypto.CryptoTransactionPort;
import com.logistics.paymentservice.application.port.out.details.UserBalancePort;
import com.logistics.paymentservice.application.port.out.fiat.FiatTransactionPort;
import com.logistics.paymentservice.domain.model.CryptoTransaction;
import com.logistics.paymentservice.domain.model.FiatTransaction;
import com.logistics.paymentservice.domain.model.UserBalance;
import com.logistics.paymentservice.infrastructure.exceptions.error.InsufficientBalanceException;
import com.logistics.paymentservice.infrastructure.exceptions.error.NoFoundUserIdException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class PaymentDetailsService implements PaymentsDetailsUseCase {

    private final UserBalancePort balanceRepository;
    private final CryptoTransactionPort cryptoTransactionRepository;
    private final FiatTransactionPort fiatTransactionRepository;

    @Override
    public List<UserBalance> getAllUserBalance(){
        return balanceRepository.findAllUserBalance();
    }

    @Override
    public void payForDelivery(String userId, BigDecimal amount) {
        UserBalance userBalance = getUserBalance(userId);

        validUserBalance(amount, userBalance);

        BigDecimal updated = userBalance.getBalance().subtract(amount);
        userBalance.setBalance(updated);
        balanceRepository.saveUserBalance(userBalance);
    }

    private void validUserBalance(BigDecimal amount, UserBalance userBalance) {
        if (userBalance.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Not enough funds to complete the delivery payment.");
        }
    }

    private UserBalance getUserBalance(String userId) {
        return balanceRepository.findBalanceByUserId(userId)
                .orElseThrow(() -> new NoFoundUserIdException(userId));
    }

    @Override
    public BigDecimal getBalance(String userId) {
        UserBalance userBalance = getUserBalance(userId);
        return userBalance.getBalance();
    }

    @Override
    public List<CryptoTransaction> getCryptoTransactions(String userId) {
        return cryptoTransactionRepository.findTransactionByUserId(userId);
    }

    @Override
    public List<CryptoTransaction> getAllCryptoTransactions() {
        return cryptoTransactionRepository.findAllTransaction();
    }

    @Override
    public List<FiatTransaction> getAllFiatTransactions(){
        return fiatTransactionRepository.getAllFiatTransactions();
    }

    @Override
    public List<FiatTransaction> getFiatTransactions(String userId){
        return fiatTransactionRepository.findTransactionByUserId(userId);
    }


}
