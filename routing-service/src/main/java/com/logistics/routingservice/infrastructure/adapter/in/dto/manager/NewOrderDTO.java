package com.logistics.routingservice.infrastructure.adapter.in.dto.manager;

import com.logistics.routingservice.infrastructure.adapter.in.dto.route.RouteResponseDTO;
import lombok.Data;

@Data
public class NewOrderDTO {
    private double capacity;
    private String userId;
    private String email;
    private RouteResponseDTO routeResponseDTO;
}
