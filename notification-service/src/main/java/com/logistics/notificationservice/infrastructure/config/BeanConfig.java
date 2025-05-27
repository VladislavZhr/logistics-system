package com.logistics.notificationservice.infrastructure.config;

import com.logistics.notificationservice.application.port.input.SendMessageUseCase;
import com.logistics.notificationservice.application.port.output.MailSenderPort;
import com.logistics.notificationservice.application.service.SendNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public SendMessageUseCase sendNotificationService(MailSenderPort mailSenderPort) {
        return new SendNotificationService(mailSenderPort);
    }
}
