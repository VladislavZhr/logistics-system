package com.logistics.userservice.infrastructure.config.bean;

import com.logistics.userservice.application.port.input.AdminToolsUseCase;
import com.logistics.userservice.application.port.output.UserDeletePort;
import com.logistics.userservice.application.port.output.UserRepositoryPort;
import com.logistics.userservice.application.service.AdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminBeanConfig {

    @Bean
    public AdminService adminService
            (
            UserRepositoryPort userRepository,
            UserDeletePort userDeletePort
            ) {
        return new AdminService(userRepository, userDeletePort);
    }

    @Bean
    public AdminToolsUseCase adminToolsUseCase(AdminService adminService) {
        return adminService;
    }
}
