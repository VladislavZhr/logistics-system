package com.logistics.routingservice.application.in.route;

import com.logistics.routingservice.domain.Route;
import com.logistics.routingservice.domain.WarehouseType;

public interface RouteUseCase {
    Route calculateRoute(double lat, double lng, WarehouseType type, double capacity);
    Route calculateDeliveryFromWarehouse(String orderId, double toLat, double toLng);
}
