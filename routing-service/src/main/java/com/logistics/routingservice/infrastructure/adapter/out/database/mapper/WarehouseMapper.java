package com.logistics.routingservice.infrastructure.adapter.out.database.mapper;

import com.logistics.routingservice.domain.Warehouse;
import com.logistics.routingservice.infrastructure.adapter.out.database.entity.WarehouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WarehouseMapper {

    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    Warehouse toDomain(WarehouseEntity warehouseEntity);
    WarehouseEntity toEntity(Warehouse warehouse);
}
