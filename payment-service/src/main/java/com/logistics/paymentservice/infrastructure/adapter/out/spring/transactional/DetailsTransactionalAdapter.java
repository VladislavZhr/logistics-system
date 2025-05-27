package com.logistics.paymentservice.infrastructure.adapter.out.spring.transactional;

import com.logistics.paymentservice.application.port.in.PaymentsDetailsUseCase;
import com.logistics.paymentservice.application.service.PaymentDetailsService;
import com.logistics.paymentservice.domain.model.CryptoTransaction;
import com.logistics.paymentservice.domain.model.FiatTransaction;
import com.logistics.paymentservice.domain.model.UserBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetailsTransactionalAdapter implements PaymentsDetailsUseCase {

    private final PaymentDetailsService paymentDetailsService;

    @Override
    public List<UserBalance> getAllUserBalance(){
        return paymentDetailsService.getAllUserBalance();
    }

    @Override
    public BigDecimal getBalance(String userId){
        return paymentDetailsService.getBalance(userId);
    }

    @Override
    public List<CryptoTransaction> getCryptoTransactions(String userId){
        return paymentDetailsService.getCryptoTransactions(userId);
    }

    @Override
    public List<CryptoTransaction> getAllCryptoTransactions(){
        return paymentDetailsService.getAllCryptoTransactions();
    }

    @Override
    public List<FiatTransaction> getFiatTransactions(String userId){
        return paymentDetailsService.getFiatTransactions(userId);
    }

    @Override
    public List<FiatTransaction> getAllFiatTransactions(){
        return paymentDetailsService.getAllFiatTransactions();
    }

    @Transactional
    @Override
    public void payForDelivery(String userId, BigDecimal amount){
        paymentDetailsService.payForDelivery(userId, amount);
    }
}
