package com.logistics.routingservice.infrastructure.adapter.out.osrm;

import com.logistics.routingservice.application.out.repoport.RouteCalculatorPort;
import com.logistics.routingservice.domain.Route;
import com.logistics.routingservice.domain.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OsrmRouteAdapter implements RouteCalculatorPort {

    private final WebClient webClient;

    @Override
    public Route calculateRoute(double userLat, double userLon, Warehouse warehouse) {
        String coordinates = String.format(
                "%f,%f;%f,%f",
                userLon, userLat, // ✅ довгота перед широтою
                warehouse.getLongitude(), warehouse.getLatitude()
        );


        Map<String, Object> osrmResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/route/v1/driving/" + coordinates) // ✅ лише шлях
                        .queryParam("overview", "full")
                        .queryParam("steps", "true")
                        .queryParam("geometries", "geojson")
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();


        Map<String, Object> route = ((List<Map<String, Object>>) osrmResponse.get("routes")).get(0);

        Number distanceRaw = (Number) route.get("distance");
        Number durationRaw = (Number) route.get("duration");

        double distance = distanceRaw.doubleValue();
        double duration = durationRaw.doubleValue();


        Map<String, Object> geometry = (Map<String, Object>) route.get("geometry");
        List<List<Double>> coords = (List<List<Double>>) geometry.get("coordinates");

        BigDecimal base = BigDecimal.valueOf(50);
        BigDecimal distanceKm = BigDecimal.valueOf(distance).divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
        BigDecimal pricePerKm = BigDecimal.valueOf(5);

        BigDecimal price = base.add(distanceKm.multiply(pricePerKm)).setScale(2, RoundingMode.HALF_UP);

        return Route.builder()
                .distanceMeters(distance)
                .durationSeconds(duration)
                .priceUah(price)
                .warehouse(warehouse)
                .geoJsonRoute(new Route.GeoJsonRoute("LineString", coords))
                .build();
    }
}
