package com.logistics.notificationservice.infrastructure.adapter.in.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {
    private String to;
    private String subject;
    private String body;
}
