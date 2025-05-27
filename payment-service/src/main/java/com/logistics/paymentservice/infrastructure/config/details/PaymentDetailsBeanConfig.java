package com.logistics.paymentservice.infrastructure.config.details;

import com.logistics.paymentservice.application.port.in.PaymentsDetailsUseCase;
import com.logistics.paymentservice.application.port.out.crypto.CryptoTransactionPort;
import com.logistics.paymentservice.application.port.out.details.UserBalancePort;
import com.logistics.paymentservice.application.port.out.fiat.FiatTransactionPort;
import com.logistics.paymentservice.application.service.PaymentDetailsService;
import com.logistics.paymentservice.infrastructure.adapter.out.spring.transactional.DetailsTransactionalAdapter;
import com.logistics.paymentservice.infrastructure.adapter.out.spring.transactional.FiatTransactionalAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentDetailsBeanConfig {

    @Bean
    public PaymentDetailsService paymentDetailsService(
            UserBalancePort userBalancePort,
            CryptoTransactionPort cryptoTransactionPort,
            FiatTransactionPort fiatTransactionPort
    ) {return new PaymentDetailsService(
            userBalancePort,
            cryptoTransactionPort,
            fiatTransactionPort
    );}

    @Bean
    public PaymentsDetailsUseCase paymentsDetailsUseCase(PaymentDetailsService paymentDetailsService) {
        return new DetailsTransactionalAdapter(paymentDetailsService);
    }
}
