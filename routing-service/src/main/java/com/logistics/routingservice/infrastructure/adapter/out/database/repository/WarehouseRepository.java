package com.logistics.routingservice.infrastructure.adapter.out.database.repository;

import com.logistics.routingservice.domain.WarehouseType;
import com.logistics.routingservice.infrastructure.adapter.out.database.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {
    Optional<WarehouseEntity> findByWarehouseId(Long warehouseId);
    List<WarehouseEntity> findByType(WarehouseType warehouseType);
}
