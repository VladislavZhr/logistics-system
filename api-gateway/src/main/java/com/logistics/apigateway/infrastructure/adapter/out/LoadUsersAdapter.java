package com.logistics.apigateway.infrastructure.adapter.out;

import com.logistics.apigateway.application.port.out.LoadUsersPort;
import com.logistics.apigateway.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoadUsersAdapter implements LoadUsersPort {

    private final WebClient webClient;

    @Override
    public Mono<List<User>> loadAllUsers() {
        return webClient.get()
                .uri("http://user-service:8081/api/admin/get-all-users")
                .retrieve()
                .bodyToFlux(User.class)
                .collectList(); // без .block()
    }
}
