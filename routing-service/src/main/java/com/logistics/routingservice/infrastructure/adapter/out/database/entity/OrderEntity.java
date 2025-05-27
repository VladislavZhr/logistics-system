package com.logistics.routingservice.infrastructure.adapter.out.database.entity;

import com.logistics.routingservice.domain.OrderStatus;
import com.logistics.routingservice.domain.WarehouseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long warehouseId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WarehouseType type;

    @Column(nullable = false)
    private Date createTime;

    private Date deliveryTime;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
