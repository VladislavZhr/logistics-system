package com.logistics.userservice.infrastructure.adapter.out.kafka.producer;

import com.logistics.userservice.application.port.output.UserDeletePort;
import com.logistics.events.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDeleteProducer implements UserDeletePort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final String topic = "user-delete";

    @Override
    public void sendDeleteUser(String userId){kafkaTemplate.send(topic, new UserEvent(userId));}
}
