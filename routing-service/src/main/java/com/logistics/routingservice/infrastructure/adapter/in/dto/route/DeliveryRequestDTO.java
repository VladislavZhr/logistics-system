package com.logistics.routingservice.infrastructure.adapter.in.dto.route;

public record DeliveryRequestDTO(String orderId, double toLat, double toLng) {
}
