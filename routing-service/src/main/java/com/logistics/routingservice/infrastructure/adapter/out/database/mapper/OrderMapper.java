package com.logistics.routingservice.infrastructure.adapter.out.database.mapper;

import com.logistics.routingservice.domain.Order;
import com.logistics.routingservice.infrastructure.adapter.out.database.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toDomain(OrderEntity entity);

//    @Mapping(source = "createTime", target = "createTime")
//    @Mapping(source = "deliveryTime", target = "deliveryTime")
    OrderEntity toEntity(Order order);
}
