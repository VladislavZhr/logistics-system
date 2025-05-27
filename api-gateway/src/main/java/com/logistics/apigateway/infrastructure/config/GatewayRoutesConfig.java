package com.logistics.apigateway.infrastructure.config;

import com.logistics.apigateway.infrastructure.config.filter.JwtGatewayFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Autowired
    private JwtGatewayFilter jwtGatewayFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user-service-public", r -> r.path("/api/auth/**")
                        .uri("http://user-service:8081"))

                .route("user-service-admin", r -> r.path("/api/admin/**")
                        .filters(f -> f.filter(jwtGatewayFilter))
                        .uri("http://user-service:8081"))

                .route("payment-service-crypto", r -> r.path("/api/crypto/**")
                        .filters(f -> f.filter(jwtGatewayFilter))
                        .uri("http://payment-service:8083"))

                .route("payment-service-fiat", r -> r.path("/api/fiat/**")
                        .filters(f -> f.filter(jwtGatewayFilter))
                        .uri("http://payment-service:8083"))

                .route("payment-service-details", r -> r.path("/api/details/**")
                        .filters(f -> f.filter(jwtGatewayFilter))
                        .uri("http://payment-service:8083"))

                .route("notification-service", r -> r.path("/api/notify/**")
                        .uri("http://notification-service:8082"))

                .route("routing-service-order", r -> r.path("/api/order/**")
                        .filters(f -> f.filter(jwtGatewayFilter))
                        .uri("http://routing-service:8084"))

                .route("payment-service-route", r -> r.path("/api/route/**")
                        .filters(f -> f.filter(jwtGatewayFilter))
                        .uri("http://routing-service:8084"))

                .route("payment-service-warehouse", r -> r.path("/api/warehouse/**")
                        .filters(f -> f.filter(jwtGatewayFilter))
                        .uri("http://routing-service:8084"))

                .build();
    }


}
