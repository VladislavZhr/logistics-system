package com.logistics.paymentservice.infrastructure.adapter.in.kafka.consumer;

import com.logistics.events.UserEvent;
import com.logistics.paymentservice.infrastructure.adapter.out.database.entity.UserBalanceEntity;

import com.logistics.paymentservice.infrastructure.adapter.out.database.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredListener {

    private final BalanceRepository userBalanceRepository;

    @KafkaListener(
            topics = "auth.user-registered",
            groupId = "payment-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUserCreated(UserEvent event) {
        log.info("📥 Отримано подію про нового користувача: {}", event.userId());

        boolean alreadyExists = userBalanceRepository.findByUserId(event.userId()).isPresent();

        if (alreadyExists) {
            log.warn("⚠️ Баланс вже існує для користувача {}", event.userId());
            return;
        }

        UserBalanceEntity userBalance = UserBalanceEntity.builder()
                .userId(event.userId())
                .balance(BigDecimal.ZERO)
                .build();

        userBalanceRepository.save(userBalance);
        log.info("✅ Баланс створено для користувача {}", event.userId());
    }
}

