package com.logistics.notificationservice.infrastructure.adapter.in.kafka.mapper;

import com.logistics.notificationservice.domain.model.Message;
import com.logistics.notificationservice.infrastructure.adapter.in.kafka.dto.MessageDTO;

public class MessageMapper {

    public static Message mapToMessage(MessageDTO messageDTO) {
        return Message.builder()
                .to(messageDTO.getTo())
                .subject(messageDTO.getSubject())
                .body(messageDTO.getBody())
                .build();
    }
}
