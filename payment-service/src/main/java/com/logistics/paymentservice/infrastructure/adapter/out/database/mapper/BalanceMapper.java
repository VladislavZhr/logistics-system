package com.logistics.paymentservice.infrastructure.adapter.out.database.mapper;

import com.logistics.paymentservice.domain.model.UserBalance;
import com.logistics.paymentservice.infrastructure.adapter.out.database.entity.UserBalanceEntity;

public class BalanceMapper {

    public static UserBalance toDomain(UserBalanceEntity userBalanceEntity){
        return UserBalance.builder()
                .id(userBalanceEntity.getId())
                .userId(userBalanceEntity.getUserId())
                .balance(userBalanceEntity.getBalance())
                .build();
    }

    public static UserBalanceEntity toEntity(UserBalance userBalance){
        return UserBalanceEntity.builder()
                .id(userBalance.getId())
                .userId(userBalance.getUserId())
                .balance(userBalance.getBalance())
                .build();
    }
}
