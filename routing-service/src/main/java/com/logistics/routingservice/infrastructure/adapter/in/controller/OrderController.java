package com.logistics.routingservice.infrastructure.adapter.in.controller;

import com.logistics.routingservice.application.in.filter.AuthorizationUseCase;
import com.logistics.routingservice.application.in.meneger.ManageOrderUseCase;
import com.logistics.routingservice.domain.Order;
import com.logistics.routingservice.infrastructure.adapter.in.dto.manager.ChangeDeliveryTimeRequest;
import com.logistics.routingservice.infrastructure.adapter.in.dto.manager.ChangeOrderStatusRequest;
import com.logistics.routingservice.infrastructure.adapter.in.dto.manager.NewOrderDTO;
import com.logistics.routingservice.infrastructure.adapter.in.dto.route.RouteResponseDTO;
import com.logistics.routingservice.infrastructure.adapter.in.mapper.RouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final ManageOrderUseCase manageOrderUseCase;
    private final AuthorizationUseCase authorizationUseCase;

    @GetMapping("/get")
    public ResponseEntity<List<Order>> getOrdersForUser(@RequestHeader ("X-User-Id") String userId) {
        return ResponseEntity.ok(manageOrderUseCase.getOrdersForUser(userId));
    }

    @PostMapping("/new-order")
    public ResponseEntity<String> newOrder(@RequestBody NewOrderDTO orderDTO) {
        manageOrderUseCase.createNewOrder(RouteMapper.toDomain(orderDTO.getRouteResponseDTO()), orderDTO.getCapacity(), orderDTO.getUserId(), orderDTO.getEmail());
        return ResponseEntity.ok("SUCCESS");
    }

    @GetMapping("/get-capacity")
    public ResponseEntity<Integer> getCapacity(@RequestParam String orderId) {
        return ResponseEntity.ok(manageOrderUseCase.getCapacity(orderId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader ("X-User-Role") String role) {
        authorizationUseCase.validRole(role);
        return ResponseEntity.ok(manageOrderUseCase.getAllOrders());
    }

    @PostMapping("/change-status")
    public ResponseEntity<String> changeOrderStatus(@RequestHeader ("X-User-Role") String role, @RequestBody ChangeOrderStatusRequest request) {
        authorizationUseCase.validRole(role);
        manageOrderUseCase.changeOrderStatus(request.orderId(), request.status());
        return ResponseEntity.ok("SUCCESS");
    }

    @PostMapping("/set-deliveryTime")
    public ResponseEntity<String> setDeliveryTime(@RequestHeader ("X-User-Role") String role, @RequestBody ChangeDeliveryTimeRequest request) {
        authorizationUseCase.validRole(role);
        manageOrderUseCase.setDeliveryTime(request.oderId(), request.deliveryTime());
        return ResponseEntity.ok("SUCCESS");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteOrder(@RequestHeader ("X-User-Role") String role, @RequestParam String orderId) {
        authorizationUseCase.validRole(role);
        manageOrderUseCase.deleteOrder(orderId);
        return ResponseEntity.ok("SUCCESS");
    }

}
