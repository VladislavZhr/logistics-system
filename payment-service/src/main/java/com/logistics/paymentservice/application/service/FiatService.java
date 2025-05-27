package com.logistics.paymentservice.application.service;

import com.logistics.paymentservice.application.port.in.FiatPaymentUseCase;
import com.logistics.paymentservice.application.port.out.details.UserBalancePort;
import com.logistics.paymentservice.application.port.out.fiat.FiatTransactionPort;
import com.logistics.paymentservice.application.port.out.fiat.StripePort;
import com.logistics.paymentservice.domain.model.*;
import com.logistics.paymentservice.domain.processed.FiatHandleProcessedDetails;
import com.logistics.paymentservice.domain.processed.FiatPaymentProcessedDetails;
import com.logistics.paymentservice.infrastructure.exceptions.error.NoFoundInvoiceIdException;
import com.logistics.paymentservice.infrastructure.exceptions.error.NoFoundUserIdException;
import com.logistics.paymentservice.infrastructure.exceptions.error.TransactionIsAlreadyProcessed;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@RequiredArgsConstructor
public class FiatService implements FiatPaymentUseCase {

    private final StripePort stripePort;
    private final FiatTransactionPort fiatTransactionPort;
    private final UserBalancePort userBalancePort;

    @Override
    public String createPayment(String userId, BigDecimal amount, String currency){
        FiatPaymentProcessedDetails paymentData = stripePort.processCreateLink(userId, currency, amount);
        FiatTransaction transaction = createTransaction(userId, amount, currency, paymentData);
        fiatTransactionPort.save(transaction);
        return paymentData.getPayUrl();
    }

    private FiatTransaction createTransaction(String userId,BigDecimal amount, String currency, FiatPaymentProcessedDetails paymentData){
        return FiatTransaction.builder()
                .userId(userId)
                .invoiceId(paymentData.getInvoiceId())
                .amount(amount)
                .currency(currency)
                .createdAt(new Date())
                .status(PaymentStatus.CREATED)
                .build();
    }

    @Override
    public void handlePayment(String payload){
        FiatHandleProcessedDetails dataPayment = stripePort.processHandlePayment(payload);
        BigDecimal amount = handleTransaction(dataPayment.sessionId(), dataPayment.invoiceId());
        handleUserBalance(dataPayment.userId(), amount);
    }

    private BigDecimal handleTransaction(String sessionId, String invoiceId){
        FiatTransaction transaction = getTransactionByInvoiceId(sessionId);
        checkAlreadyProcessedStatus(transaction);
        handleDatalistStatus(transaction, invoiceId);
        return transaction.getAmount();
    }

    @Override
    public void validatePayment(String sigHeader, String payload){
        stripePort.validatePayment(sigHeader, payload);
    }


    private void handleDatalistStatus(FiatTransaction transaction, String invoiceId){
        transaction.setStatus(PaymentStatus.SUCCESS);
        transaction.setInvoiceId(invoiceId);
        fiatTransactionPort.save(transaction);
    }

    private FiatTransaction getTransactionByInvoiceId(String invoiceId) {
        return fiatTransactionPort.findByInvoiceId(invoiceId)
                .orElseThrow(()-> new NoFoundInvoiceIdException("Not found invoice id: " + invoiceId));
    }

    private void checkAlreadyProcessedStatus(FiatTransaction transaction){
        if (transaction.getStatus().equals(PaymentStatus.SUCCESS)) throw new TransactionIsAlreadyProcessed("Transaction is already processed.");
    }

    private void handleUserBalance(String userId, BigDecimal amount){
        UserBalance user = getUserBalanceById(userId);
        user.setBalance(user.getBalance().add(amount));
        userBalancePort.saveUserBalance(user);
    }

    private UserBalance getUserBalanceById(String userId) {
        return userBalancePort.findBalanceByUserId(userId)
                .orElseThrow(() -> new NoFoundUserIdException("Not found user id: " + userId));
    }
}
