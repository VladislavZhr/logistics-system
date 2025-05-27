package com.logistics.routingservice.infrastructure.adapter.in.dto.route;

import com.logistics.routingservice.domain.WarehouseType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class RouteResponseDTO {
    private double distanceMeters;
    private double durationSeconds;
    private BigDecimal priceUah;

    private WarehouseInfo warehouse;
    private GeoJsonRoute geoJsonRoute;

    @Data
    @AllArgsConstructor
    public static class WarehouseInfo {
        private Long warehouseId;
        private String name;
        private double latitude;
        private double longitude;
        private WarehouseType type;
    }

    @Data
    @AllArgsConstructor
    public static class GeoJsonRoute {
        private String type; // "LineString"
        private List<List<Double>> coordinates;
    }
}
