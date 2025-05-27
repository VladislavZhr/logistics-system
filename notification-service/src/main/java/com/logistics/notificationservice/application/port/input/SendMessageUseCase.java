package com.logistics.notificationservice.application.port.input;

import com.logistics.notificationservice.domain.model.Message;

public interface SendMessageUseCase {
    void sendMessageForgetPassword(Message message);
}
