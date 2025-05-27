package com.logistics.routingservice.application.service;

import com.logistics.routingservice.application.in.route.RouteUseCase;
import com.logistics.routingservice.application.out.repoport.OrderRepoPort;
import com.logistics.routingservice.application.out.repoport.RouteCalculatorPort;
import com.logistics.routingservice.application.out.repoport.WarehouseRepoPort;
import com.logistics.routingservice.domain.*;
import com.logistics.routingservice.infrastructure.execeptions.error.NoAvailableWarehouseException;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class RouteService implements RouteUseCase {

    private final WarehouseRepoPort warehouseRepository;
    private final RouteCalculatorPort routeCalculator;
    private final OrderRepoPort orderRepository;

    @Override
    public Route calculateRoute(double lat, double lng, WarehouseType type, double capacity){

        List<Warehouse> filtered = filterWarehouse(type, capacity);
        checkWarehouseNotEmpty(filtered);
        Warehouse warehouse = findNearWarehouse(filtered, lat, lng);

        return routeCalculator.calculateRoute(lat, lng, warehouse);
    }

    @Override
    public Route calculateDeliveryFromWarehouse(String orderId, double toLat, double toLng) {
        Order order = findOrderById(orderId);
        Warehouse warehouse = getWarehouseById(order.getWarehouseId().toString());
        orderRepository.deleteOrder(orderId);

        return routeCalculator.calculateRoute(toLat, toLng, warehouse);
    }

    private Order findOrderById(String orderId) {
        return orderRepository.getOrderByOrderId(orderId);
    }

    private List<Warehouse> filterWarehouse(WarehouseType type, double capacity){
        return warehouseRepository.findByType(type).stream()
                .filter(w -> (w.getMaxCapacity() - w.getCurrentLoad()) >= capacity)
                .toList();
    }

    private Warehouse findNearWarehouse(List<Warehouse> filtered, double lat, double lng){
        return filtered.stream()
                .min(Comparator.comparingDouble(w ->
                        haversine(lat, lng, w.getLatitude(), w.getLongitude())))
                .orElseThrow();
    }

    private void checkWarehouseNotEmpty(List<Warehouse> warehouses){
        if (warehouses.isEmpty()){
            throw new NoAvailableWarehouseException("No available warehouse!");
        }
    }

    private Warehouse getWarehouseById(String warehouseId){return warehouseRepository.getWarehouseById(warehouseId);}

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
