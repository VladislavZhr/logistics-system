package com.logistics.routingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse  {
    private Long warehouseId;
    private String address;
    private double latitude;
    private double longitude;
    private int maxCapacity;
    private double currentLoad;
    private String responsibleContact;
    private WarehouseType type;
}
