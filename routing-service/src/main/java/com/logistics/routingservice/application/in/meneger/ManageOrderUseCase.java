package com.logistics.routingservice.application.in.meneger;

import com.logistics.routingservice.domain.Order;
import com.logistics.routingservice.domain.OrderStatus;
import com.logistics.routingservice.domain.Route;

import java.util.Date;
import java.util.List;

public interface ManageOrderUseCase {
    void deleteOrder(String orderId);
    void changeOrderStatus(String orderId, OrderStatus orderStatus);
    List<Order> getOrdersForUser(String userId);
    List<Order> getAllOrders();
    void setDeliveryTime(String orderId, Date deliveryTime);
    void createNewOrder(Route route, double capacity, String userId, String email);
    Integer getCapacity(String orderId);
}
