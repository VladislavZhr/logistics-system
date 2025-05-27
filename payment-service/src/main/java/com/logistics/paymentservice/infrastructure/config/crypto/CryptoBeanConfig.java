package com.logistics.paymentservice.infrastructure.config.crypto;

import com.logistics.paymentservice.application.port.in.CryptoPaymentUseCase;
import com.logistics.paymentservice.application.port.out.crypto.CryptoCloudPort;
import com.logistics.paymentservice.application.port.out.crypto.CryptoTransactionPort;
import com.logistics.paymentservice.application.port.out.details.UserBalancePort;
import com.logistics.paymentservice.application.port.out.details.ValidateCryptoTokenPort;
import com.logistics.paymentservice.application.service.CryptoService;
import com.logistics.paymentservice.infrastructure.adapter.out.spring.transactional.CryptoTransactionalAdapter;
import com.logistics.paymentservice.infrastructure.adapter.out.spring.transactional.FiatTransactionalAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CryptoBeanConfig {

    @Bean
    public RestTemplate restTemplate() {return new RestTemplate();}

    @Bean
    public CryptoService cryptoService(
            CryptoTransactionPort cryptoRepository,
            CryptoCloudPort cryptoCloudPort,
            UserBalancePort userBalanceRepository,
            ValidateCryptoTokenPort validateCryptoTokenPort
    ) {
        return new CryptoService(
            cryptoRepository,
            cryptoCloudPort,
            userBalanceRepository,
            validateCryptoTokenPort
    );
    }

    @Bean
    public CryptoPaymentUseCase cryptoPaymentUseCase(CryptoService cryptoService) {
        return new CryptoTransactionalAdapter(cryptoService);
    }
}
