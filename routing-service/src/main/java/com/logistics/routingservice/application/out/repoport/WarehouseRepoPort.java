package com.logistics.routingservice.application.out.repoport;

import com.logistics.routingservice.domain.Warehouse;
import com.logistics.routingservice.domain.WarehouseType;

import java.util.List;

public interface WarehouseRepoPort {
    void save(Warehouse warehouse);
    List<Warehouse> getAllWarehouses();
    Warehouse getWarehouseById(String warehouseId);
    List<Warehouse> findByType(WarehouseType type);

}
