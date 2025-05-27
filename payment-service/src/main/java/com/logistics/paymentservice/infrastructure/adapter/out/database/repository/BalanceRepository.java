package com.logistics.paymentservice.infrastructure.adapter.out.database.repository;

import com.logistics.paymentservice.infrastructure.adapter.out.database.entity.UserBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<UserBalanceEntity, Long> {
    Optional<UserBalanceEntity> findByUserId(String userId);
}
