package com.logistics.routingservice.infrastructure.config.beans;

import com.logistics.routingservice.application.in.filter.AuthorizationUseCase;
import com.logistics.routingservice.application.in.meneger.ManageOrderUseCase;
import com.logistics.routingservice.application.in.meneger.WarehouseUseCase;
import com.logistics.routingservice.application.out.repoport.OrderRepoPort;
import com.logistics.routingservice.application.out.repoport.WarehouseRepoPort;
import com.logistics.routingservice.application.service.ManagerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerBeansConfig {

    @Bean
    public ManagerService managerService(
            OrderRepoPort orderRepository,
            WarehouseRepoPort warehouseRepository
    ) {
        return new ManagerService(
                orderRepository,
                warehouseRepository
        );
    }

    @Bean
    public ManageOrderUseCase manageOrderUseCase(ManagerService managerService) {
        return managerService;
    }

    @Bean
    public WarehouseUseCase warehouseUseCase(ManagerService managerService) {
        return managerService;
    }

    @Bean
    AuthorizationUseCase authorizationUseCase(ManagerService managerService) {
        return managerService;
    }
}
