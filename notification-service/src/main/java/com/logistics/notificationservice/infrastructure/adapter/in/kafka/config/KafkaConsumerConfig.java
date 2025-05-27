package com.logistics.notificationservice.infrastructure.adapter.in.kafka.config;

import com.logistics.events.PasswordResetRequestedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, PasswordResetRequestedEvent> passwordResetConsumerFactory() {
        JsonDeserializer<PasswordResetRequestedEvent> deserializer = new JsonDeserializer<>(PasswordResetRequestedEvent.class);
        deserializer.addTrustedPackages("*"); // дозволяє десеріалізацію будь-якого пакету

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PasswordResetRequestedEvent> passwordResetKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PasswordResetRequestedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(passwordResetConsumerFactory());
        return factory;
    }
}
