package com.logistics.userservice.application.port.output;

import com.logistics.userservice.infrastructure.adapter.out.kafka.event.PasswordResetRequestedEvent;

public interface NotificationEventPort {
    void sendRestEvent (String token, String email);
}
