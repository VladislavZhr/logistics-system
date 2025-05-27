package com.logistics.userservice.infrastructure.adapter.out.kafka.producer;

import com.logistics.userservice.application.port.output.NotificationEventPort;
import lombok.RequiredArgsConstructor;
import com.logistics.events.PasswordResetRequestedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetProducer implements NotificationEventPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final String topic = "auth.password-reset-requested";

    @Override
    public void sendRestEvent(String token, String email) {
        kafkaTemplate.send(topic, new PasswordResetRequestedEvent(email, token));
    }
}
