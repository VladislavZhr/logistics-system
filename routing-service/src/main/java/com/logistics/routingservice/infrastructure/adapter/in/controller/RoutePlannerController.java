package com.logistics.routingservice.infrastructure.adapter.in.controller;

import com.logistics.routingservice.application.in.route.RouteUseCase;
import com.logistics.routingservice.infrastructure.adapter.in.dto.route.DeliveryRequestDTO;
import com.logistics.routingservice.infrastructure.adapter.in.dto.route.RouteRequestDTO;
import com.logistics.routingservice.infrastructure.adapter.in.dto.route.RouteResponseDTO;
import com.logistics.routingservice.infrastructure.adapter.in.mapper.RouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
@RequiredArgsConstructor
public class RoutePlannerController {

    private final RouteUseCase routeUseCase;

    @PostMapping("/to-warehouse")
    public ResponseEntity<RouteResponseDTO> getRouteAndPrice(
            @RequestBody RouteRequestDTO routeRequestDTO
    ) {
       RouteResponseDTO routeResponseDTO = RouteMapper.toDTO(routeUseCase.calculateRoute(
               routeRequestDTO.lat(),
               routeRequestDTO.lng(),
               routeRequestDTO.type(),
               routeRequestDTO.capacity())
       );

       return ResponseEntity.ok(routeResponseDTO);
    }

    @PostMapping("/from-warehouse")
    public ResponseEntity<RouteResponseDTO> getRouteFromWarehouse(@RequestBody DeliveryRequestDTO deliveryRequestDTO) {
        RouteResponseDTO routeResponseDTO = RouteMapper.toDTO(routeUseCase.calculateDeliveryFromWarehouse(
                deliveryRequestDTO.orderId(),
                deliveryRequestDTO.toLat(),
                deliveryRequestDTO.toLng())
        );
        return ResponseEntity.ok(routeResponseDTO);
    }
}
