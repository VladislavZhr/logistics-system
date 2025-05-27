package com.logistics.routingservice.application.out.repoport;

import com.logistics.routingservice.domain.Route;
import com.logistics.routingservice.domain.Warehouse;

public interface RouteCalculatorPort {
    Route calculateRoute(double userLat, double userLon, Warehouse warehouse);
}
