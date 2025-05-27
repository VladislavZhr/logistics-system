package com.logistics.paymentservice.infrastructure.adapter.in.kafka.consumer;

import com.logistics.events.UserEvent;
import com.logistics.paymentservice.infrastructure.adapter.out.database.entity.UserBalanceEntity;
import com.logistics.paymentservice.infrastructure.adapter.out.database.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDeleteListener {

    private final BalanceRepository balanceRepository;

    @KafkaListener(
            topics = "user-delete",
            groupId = "payment-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserDelete(UserEvent event) {
        log.info("📥 Отримано подію про видалення користувача: {}", event.userId());

        boolean userExists = balanceRepository.findByUserId(event.userId()).isPresent();

        if (userExists) {
            UserBalanceEntity userBalanceEntity = balanceRepository.findByUserId(event.userId()).get();
            balanceRepository.delete(userBalanceEntity);
            log.warn("Користувача успішно видалено! {}", event.userId());
        }

        log.warn("Користувача з даним айді не існує {}", event.userId());
    }
}
