package com.logistics.userservice.infrastructure.adapter.out.persistence.mapper;

import com.logistics.userservice.domain.model.User;
import com.logistics.userservice.infrastructure.adapter.out.persistence.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(User user){
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static User toDomain(UserEntity entity){
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .build();
    }
}
