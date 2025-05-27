package com.logistics.routingservice.infrastructure.adapter.in.dto.route;

import com.logistics.routingservice.domain.WarehouseType;

public record RouteRequestDTO(String email, double lat, double lng, WarehouseType type, double capacity) {
}
