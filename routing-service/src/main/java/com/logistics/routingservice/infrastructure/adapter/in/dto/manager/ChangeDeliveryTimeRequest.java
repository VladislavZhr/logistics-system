package com.logistics.routingservice.infrastructure.adapter.in.dto.manager;

import java.util.Date;

public record ChangeDeliveryTimeRequest(String oderId, Date deliveryTime) {
}
