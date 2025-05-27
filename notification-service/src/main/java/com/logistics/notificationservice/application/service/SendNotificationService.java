package com.logistics.notificationservice.application.service;

import com.logistics.notificationservice.application.port.input.SendMessageUseCase;
import com.logistics.notificationservice.application.port.output.MailSenderPort;
import com.logistics.notificationservice.domain.model.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendNotificationService implements SendMessageUseCase {

    private final MailSenderPort mailSenderPort;

    @Override
    public void sendMessageForgetPassword(Message message) {
        mailSenderPort.sendMail(message.getTo(), message.getSubject(), message.getBody());
    }
}
