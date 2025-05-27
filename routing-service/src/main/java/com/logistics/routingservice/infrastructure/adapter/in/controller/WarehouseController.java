package com.logistics.routingservice.infrastructure.adapter.in.controller;

import com.logistics.routingservice.application.in.meneger.WarehouseUseCase;
import com.logistics.routingservice.domain.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseUseCase warehouseUseCase;

    @GetMapping
    public ResponseEntity<List<Warehouse>> getWarehouses() {
        return ResponseEntity.ok(warehouseUseCase.getWarehouses());
    }
}
