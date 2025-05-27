package com.logistics.routingservice.infrastructure.adapter.out.database.repository;

import com.logistics.routingservice.domain.Order;
import com.logistics.routingservice.infrastructure.adapter.out.database.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderId(Long orderId);
    List<OrderEntity> findByUserId(String userId);

    @Modifying
    @Query("DELETE FROM OrderEntity o WHERE o.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);


}
