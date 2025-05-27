package com.logistics.paymentservice.infrastructure.adapter.out.spring.transactional;

import com.logistics.paymentservice.application.port.in.CryptoPaymentUseCase;
import com.logistics.paymentservice.application.service.CryptoService;
import com.logistics.paymentservice.domain.model.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CryptoTransactionalAdapter implements CryptoPaymentUseCase {

    private final CryptoService cryptoService;

    @Override
    public String createPayment(String userId, BigDecimal amount, String currency){
        return cryptoService.createPayment(userId, amount, currency);
    }

    @Transactional
    @Override
    public void handlePayment(PaymentStatus status, String invoiceId, String cryptoCurrency){
        cryptoService.handlePayment(status, invoiceId, cryptoCurrency);
    }

    @Override
    public void validateToken(String token, String invoiceId){
        cryptoService.validateToken(token, invoiceId);
    }
}
