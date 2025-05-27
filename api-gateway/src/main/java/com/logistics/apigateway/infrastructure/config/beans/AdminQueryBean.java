package com.logistics.apigateway.infrastructure.config.beans;

import com.logistics.apigateway.application.port.in.AdminQueryUseCase;
import com.logistics.apigateway.application.port.out.LoadBalancesPort;
import com.logistics.apigateway.application.port.out.LoadUsersPort;
import com.logistics.apigateway.application.service.AdminUserAggregationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AdminQueryBean {

    @Bean
    public AdminUserAggregationService adminUserAggregationService(
             LoadUsersPort loadUsersPort,
          LoadBalancesPort loadBalancesPort
    ) {return new AdminUserAggregationService(
            loadUsersPort,
            loadBalancesPort
    );}

    @Bean
    public AdminQueryUseCase adminQueryUseCase(AdminUserAggregationService adminUserAggregationService){
        return adminUserAggregationService;
    }

    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }
}
