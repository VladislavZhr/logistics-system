package com.logistics.routingservice.infrastructure.adapter.out.database.entity;

import com.logistics.routingservice.domain.WarehouseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private int maxCapacity;

    @Column(nullable = false)
    private int currentLoad;

    @Column(nullable = false)
    private String responsibleContact;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WarehouseType type;
}
