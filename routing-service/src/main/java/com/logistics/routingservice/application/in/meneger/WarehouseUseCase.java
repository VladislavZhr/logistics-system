package com.logistics.routingservice.application.in.meneger;

import com.logistics.routingservice.domain.Warehouse;

import java.util.List;

public interface WarehouseUseCase {
    List<Warehouse> getWarehouses();
}
