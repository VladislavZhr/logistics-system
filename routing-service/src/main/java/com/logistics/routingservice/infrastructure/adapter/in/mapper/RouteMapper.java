package com.logistics.routingservice.infrastructure.adapter.in.mapper;

import com.logistics.routingservice.domain.Route;
import com.logistics.routingservice.domain.Warehouse;
import com.logistics.routingservice.infrastructure.adapter.in.dto.route.RouteResponseDTO;

public class RouteMapper {

    public static Route toDomain(RouteResponseDTO dto){
        Warehouse warehouse = Warehouse.builder()
                .warehouseId(dto.getWarehouse().getWarehouseId())
                .address(dto.getWarehouse().getName())
                .latitude(dto.getWarehouse().getLatitude())
                .longitude(dto.getWarehouse().getLongitude())
                .type(dto.getWarehouse().getType())
                .build();

        Route.GeoJsonRoute geoJsonRoute = Route.GeoJsonRoute.builder()
                .type(dto.getGeoJsonRoute().getType())
                .coordinates(dto.getGeoJsonRoute().getCoordinates())
                .build();

        return Route.builder()
                .distanceMeters(dto.getDistanceMeters())
                .durationSeconds(dto.getDurationSeconds())
                .priceUah(dto.getPriceUah())
                .warehouse(warehouse)
                .geoJsonRoute(geoJsonRoute)
                .build();
    }

    public static RouteResponseDTO toDTO(Route route) {
        RouteResponseDTO.WarehouseInfo warehouseInfo = new RouteResponseDTO.WarehouseInfo(
                route.getWarehouse().getWarehouseId(),
                route.getWarehouse().getAddress(),
                route.getWarehouse().getLatitude(),
                route.getWarehouse().getLongitude(),
                route.getWarehouse().getType()
        );

        RouteResponseDTO.GeoJsonRoute geoJson = new RouteResponseDTO.GeoJsonRoute(
                route.getGeoJsonRoute().getType(),
                route.getGeoJsonRoute().getCoordinates()
        );

        return new RouteResponseDTO(
                route.getDistanceMeters(),
                route.getDurationSeconds(),
                route.getPriceUah(),
                warehouseInfo,
                geoJson
        );
    }

}
