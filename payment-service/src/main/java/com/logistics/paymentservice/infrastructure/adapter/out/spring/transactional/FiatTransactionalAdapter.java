package com.logistics.paymentservice.infrastructure.adapter.out.spring.transactional;

import com.logistics.paymentservice.application.port.in.FiatPaymentUseCase;
import com.logistics.paymentservice.application.service.FiatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FiatTransactionalAdapter implements FiatPaymentUseCase {

    private final FiatService fiatService;

    @Transactional
    @Override
    public void handlePayment(String payload){
        fiatService.handlePayment(payload);
    }

    @Override
    public String createPayment(String userId, BigDecimal amount, String currency) {
        return fiatService.createPayment(userId, amount, currency);
    }

    @Override
    public void validatePayment(String sigHeader, String payload) {
        fiatService.validatePayment(sigHeader, payload);
    }
}
