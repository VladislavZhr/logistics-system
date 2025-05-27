package com.logistics.paymentservice.infrastructure.config.fiat;

import com.logistics.paymentservice.application.port.in.FiatPaymentUseCase;
import com.logistics.paymentservice.application.port.out.details.UserBalancePort;
import com.logistics.paymentservice.application.port.out.fiat.FiatTransactionPort;
import com.logistics.paymentservice.application.port.out.fiat.StripePort;
import com.logistics.paymentservice.application.service.FiatService;
import com.logistics.paymentservice.infrastructure.adapter.out.spring.transactional.FiatTransactionalAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiatBeanConfig {

    @Bean
    public FiatService fiatService(
            StripePort stripePort,
            FiatTransactionPort fiatTransactionPort,
            UserBalancePort userBalancePort
    ) {
        return new FiatService(
                stripePort,
                fiatTransactionPort,
                userBalancePort
        );
    }

    @Bean
    public FiatPaymentUseCase fiatPaymentUseCase(FiatService fiatService) {
        return new FiatTransactionalAdapter(fiatService);
    }
}
