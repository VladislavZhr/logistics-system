package com.logistics.routingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Route {
    private double distanceMeters;
    private double durationSeconds;
    private BigDecimal priceUah;
    private Warehouse warehouse;
    private GeoJsonRoute geoJsonRoute;

    @Data
    @AllArgsConstructor
    @Builder
    public static class GeoJsonRoute {
        private String type;
        private List<List<Double>> coordinates;
    }
}
