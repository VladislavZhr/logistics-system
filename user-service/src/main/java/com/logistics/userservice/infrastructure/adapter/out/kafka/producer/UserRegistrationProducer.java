package com.logistics.userservice.infrastructure.adapter.out.kafka.producer;

import com.logistics.userservice.application.port.output.UserRegistrationBalancePort;
import com.logistics.events.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegistrationProducer implements UserRegistrationBalancePort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final String topic = "auth.user-registered";

    public void sendUserRegisteredEvent(String userId) {
        kafkaTemplate.send(topic, new UserEvent(userId));
    }
}
