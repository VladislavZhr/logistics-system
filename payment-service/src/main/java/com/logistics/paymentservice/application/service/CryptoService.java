package com.logistics.paymentservice.application.service;

import com.logistics.paymentservice.application.port.in.CryptoPaymentUseCase;
import com.logistics.paymentservice.application.port.out.crypto.CryptoCloudPort;
import com.logistics.paymentservice.application.port.out.crypto.CryptoTransactionPort;
import com.logistics.paymentservice.application.port.out.details.UserBalancePort;
import com.logistics.paymentservice.application.port.out.details.ValidateCryptoTokenPort;
import com.logistics.paymentservice.domain.model.CryptoTransaction;
import com.logistics.paymentservice.domain.model.PaymentStatus;
import com.logistics.paymentservice.domain.model.UserBalance;
import com.logistics.paymentservice.infrastructure.exceptions.error.NoFoundInvoiceIdException;
import com.logistics.paymentservice.infrastructure.exceptions.error.NoFoundUserIdException;
import com.logistics.paymentservice.infrastructure.exceptions.error.TransactionIsAlreadyProcessed;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@RequiredArgsConstructor
public class CryptoService implements CryptoPaymentUseCase {

    private final CryptoTransactionPort cryptoRepository;
    private final CryptoCloudPort cryptoCloudPort;
    private final UserBalancePort userBalanceRepository;
    private final ValidateCryptoTokenPort validateCryptoTokenPort;

    @Override
    public String createPayment(String userId, BigDecimal amount, String currency) {
        var payment = cryptoCloudPort.processCreateLink(userId, amount, currency);
        CryptoTransaction transaction = createCryptoTransaction(userId, payment.getInvoiceId(), amount, currency);
        cryptoRepository.save(transaction);
        return payment.getPay_url();
    }

    private CryptoTransaction createCryptoTransaction(
            String userId,
            String invoiceId,
            BigDecimal amount,
            String currency
    ) {
        return CryptoTransaction.builder()
                .userId(userId)
                .invoiceId(invoiceId)
                .amount(amount)
                .currency(currency)
                .createdAt(new Date())
                .status(PaymentStatus.CREATED)
                .build();
    }

    @Override
    public void handlePayment(PaymentStatus status, String invoiceId, String cryptoCurrency){
        CryptoTransaction cryptoTransaction = getTransactionByInvoiceId(invoiceId);
        checkAlreadyProcessedStatus(cryptoTransaction);
        handleDetailsTransaction(cryptoTransaction, status, cryptoCurrency);
        if (status.equals(PaymentStatus.SUCCESS)){
            handleDetailsUserBalance(cryptoTransaction);
        }
    }

    private void handleDetailsUserBalance(CryptoTransaction cryptoTransaction) {
        UserBalance userBalance = findUserBalanceByUserId(cryptoTransaction);
        userBalance.setBalance(userBalance.getBalance().add(cryptoTransaction.getAmount()));
        userBalanceRepository.saveUserBalance(userBalance);
    }

    private UserBalance findUserBalanceByUserId(CryptoTransaction cryptoTransaction){
        return userBalanceRepository.findBalanceByUserId(cryptoTransaction.getUserId())
                .orElseThrow(()-> new NoFoundUserIdException("Not found user id: " + cryptoTransaction.getUserId()));
    }

    private CryptoTransaction getTransactionByInvoiceId(String invoiceId){
        return cryptoRepository.findByInvoiceId(invoiceId)
                .orElseThrow(()-> new NoFoundInvoiceIdException("Not found invoice id: " + invoiceId));
    }

    private void handleDetailsTransaction(CryptoTransaction cryptoTransaction, PaymentStatus status, String cryptoCurrency) {
        cryptoTransaction.setStatus(status);
        cryptoTransaction.setCryptocurrency(cryptoCurrency);
        cryptoRepository.save(cryptoTransaction);
    }


    private void checkAlreadyProcessedStatus(CryptoTransaction cryptoTransaction){
        if (cryptoTransaction.getStatus().equals(PaymentStatus.SUCCESS)) throw new TransactionIsAlreadyProcessed("Transaction is already processed.");
    }

    @Override
    public void validateToken(String token, String invoiceId){
        validateCryptoTokenPort.validateToken(token, invoiceId);
    }
}
