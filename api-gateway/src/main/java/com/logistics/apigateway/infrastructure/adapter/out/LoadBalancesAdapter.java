package com.logistics.apigateway.infrastructure.adapter.out;

import com.logistics.apigateway.application.port.out.LoadBalancesPort;
import com.logistics.apigateway.domain.model.UserBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoadBalancesAdapter implements LoadBalancesPort {

    private final WebClient webClient;

    @Override
    public Mono<List<UserBalance>> loadAllBalances() {
        return webClient.get()
                .uri("http://payment-service:8083/api/details/all/balance")
                .retrieve()
                .bodyToFlux(UserBalance.class)
                .collectList(); // без .block()
    }
}

