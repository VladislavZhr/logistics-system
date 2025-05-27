package com.logistics.routingservice.infrastructure.adapter.out.kafka.consumer;

import com.logistics.routingservice.application.out.repoport.OrderRepoPort;
import com.logistics.events.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserDeleteListener {

    private final OrderRepoPort orderRepository;

    @KafkaListener(
            topics = "user-delete",
            groupId = "payment-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @KafkaListener(
            topics = "user-delete",
            groupId = "route-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserDelete(UserEvent event) {
        String userId = event.userId();
        log.info("📥 Отримано подію про видалення користувача: {}", userId);
        orderRepository.deleteByUserId(userId);
        log.info("✅ Видалено всі замовлення користувача з ID {}", userId);
    }

}

