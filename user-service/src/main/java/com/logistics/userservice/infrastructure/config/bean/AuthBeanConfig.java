package com.logistics.userservice.infrastructure.config.bean;

import com.logistics.userservice.application.port.input.PasswordUseCase;
import com.logistics.userservice.application.port.input.UserUseCase;
import com.logistics.userservice.application.port.output.*;
import com.logistics.userservice.application.service.AuthService;
import com.logistics.userservice.application.port.input.AuthUseCase;
import com.logistics.userservice.infrastructure.jwt.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthBeanConfig {


    @Bean
    public AuthService authService(UserRepositoryPort userRepositoryPort,
                                   JwtService jwtService,
                                   PasswordEncoderPort passwordEncoder,
                                   ForgetTokenPort tokenPort,
                                   NotificationEventPort notificationEventPort,
                                   UserRegistrationBalancePort userRegistrationBalancePort
    ) {
        return new AuthService(
                userRepositoryPort,
                jwtService,
                passwordEncoder,
                tokenPort,
                notificationEventPort,
                userRegistrationBalancePort
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthUseCase authUseCase(AuthService authService) {
        return authService;
    }

    @Bean
    public UserUseCase userUseCase(AuthService authService) {
        return authService;
    }

    @Bean
    public PasswordUseCase passwordUseCase(AuthService authService) {
        return authService;
    }


}
