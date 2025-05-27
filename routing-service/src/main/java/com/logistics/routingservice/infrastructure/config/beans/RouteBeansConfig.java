package com.logistics.routingservice.infrastructure.config.beans;

import com.logistics.routingservice.application.in.route.RouteUseCase;
import com.logistics.routingservice.application.out.repoport.OrderRepoPort;
import com.logistics.routingservice.application.out.repoport.RouteCalculatorPort;
import com.logistics.routingservice.application.out.repoport.WarehouseRepoPort;
import com.logistics.routingservice.application.service.RouteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RouteBeansConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://osrm:5000")
                .build();
    }

    @Bean
    public RouteService routeService(
            WarehouseRepoPort warehouseRepoPort,
            RouteCalculatorPort routeCalculatorPort,
            OrderRepoPort orderRepoPort
    ) {
        return new RouteService(
                warehouseRepoPort,
                routeCalculatorPort,
                orderRepoPort
        );
    }

    @Bean
    public RouteUseCase routeUseCase(RouteService routeService) {
        return routeService;
    }
}
