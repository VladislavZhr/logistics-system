package com.logistics.notificationservice.application.port.output;

import com.logistics.notificationservice.domain.model.Message;

public interface MailSenderPort {
    void sendMail(String to, String subject, String body);
}
