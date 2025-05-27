package com.logistics.userservice.infrastructure.adapter.out.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordResetRequestedEvent {
    private String email;
    private String token;
}
