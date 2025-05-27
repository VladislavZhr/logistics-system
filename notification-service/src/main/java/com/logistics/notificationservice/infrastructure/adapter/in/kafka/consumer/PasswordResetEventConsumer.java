package com.logistics.notificationservice.infrastructure.adapter.in.kafka.consumer;

import com.logistics.notificationservice.application.port.input.SendMessageUseCase;
import com.logistics.events.PasswordResetRequestedEvent;
import com.logistics.notificationservice.infrastructure.adapter.in.kafka.dto.MessageDTO;
import com.logistics.notificationservice.infrastructure.adapter.in.kafka.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetEventConsumer {

    private final SendMessageUseCase sendMessageUseCase;

    @KafkaListener(
            topics = "auth.password-reset-requested",
            groupId = "notification-group",
            containerFactory = "passwordResetKafkaListenerFactory"
    )
    public void listen(PasswordResetRequestedEvent event) {
        String subject = "Password Reset Request";
        String body = "To reset your password, click the link: http://localhost:3001/forgotpassword?token=" + event.getToken();

        MessageDTO message = new MessageDTO(event.getEmail(), subject, body);
        sendMessageUseCase.sendMessageForgetPassword(MessageMapper.mapToMessage(message));
    }
}
