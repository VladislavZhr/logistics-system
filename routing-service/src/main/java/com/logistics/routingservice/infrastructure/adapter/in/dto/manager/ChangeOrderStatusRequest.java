package com.logistics.routingservice.infrastructure.adapter.in.dto.manager;

import com.logistics.routingservice.domain.OrderStatus;

public record ChangeOrderStatusRequest(String orderId, OrderStatus status) {
}
