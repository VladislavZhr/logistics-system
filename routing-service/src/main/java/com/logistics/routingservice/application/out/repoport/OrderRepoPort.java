package com.logistics.routingservice.application.out.repoport;

import com.logistics.routingservice.domain.Order;
import com.logistics.routingservice.domain.OrderStatus;

import java.util.List;

public interface OrderRepoPort {
    void saveOrder(Order order);
    void deleteOrder(String orderId);
    List<Order> getOrdersByUserId(String userId);
    List<Order> getAllOrders();
    Order getOrderByOrderId(String orderId);
    void deleteByUserId(String userId);

}
