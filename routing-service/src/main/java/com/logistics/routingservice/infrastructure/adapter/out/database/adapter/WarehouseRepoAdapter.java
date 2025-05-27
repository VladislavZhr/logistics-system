package com.logistics.routingservice.infrastructure.adapter.out.database.adapter;

import com.logistics.routingservice.application.out.repoport.WarehouseRepoPort;
import com.logistics.routingservice.domain.Warehouse;
import com.logistics.routingservice.domain.WarehouseType;
import com.logistics.routingservice.infrastructure.adapter.out.database.entity.WarehouseEntity;
import com.logistics.routingservice.infrastructure.adapter.out.database.mapper.WarehouseMapper;
import com.logistics.routingservice.infrastructure.adapter.out.database.repository.WarehouseRepository;
import com.logistics.routingservice.infrastructure.execeptions.error.NoExistingWarehouseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WarehouseRepoAdapter implements WarehouseRepoPort {

    private final WarehouseRepository repository;

    @Override
    public void save(Warehouse warehouse) {
        repository.save(WarehouseMapper.INSTANCE.toEntity(warehouse));
    }

    @Override
    public List<Warehouse> findByType(WarehouseType type) {
        return repository.findByType(type).stream()
                .map(WarehouseMapper.INSTANCE::toDomain)
                .toList();
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        return repository.findAll().stream()
                .map(WarehouseMapper.INSTANCE::toDomain)
                .toList();
    }

    @Override
    public Warehouse getWarehouseById(String warehouseId) {
        WarehouseEntity warehouseEntity = repository.findByWarehouseId(Long.parseLong(warehouseId))
                .orElseThrow(() -> new NoExistingWarehouseException(""));
        return WarehouseMapper.INSTANCE.toDomain(warehouseEntity);
    }

}
