package com.logistics.routingservice.domain;

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
public class Order {
    private Long orderId;
    private Long warehouseId;
    private String userEmail;
    private String userId;
    private Date createTime;
    private Date deliveryTime;
    private String address;
    private double distance;
    private int weight;
    private WarehouseType type;
    private BigDecimal price;
    private OrderStatus status;
}
