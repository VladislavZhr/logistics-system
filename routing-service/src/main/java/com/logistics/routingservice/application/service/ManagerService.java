package com.logistics.routingservice.application.service;

import com.logistics.routingservice.application.in.filter.AuthorizationUseCase;
import com.logistics.routingservice.application.in.meneger.ManageOrderUseCase;
import com.logistics.routingservice.application.in.meneger.WarehouseUseCase;
import com.logistics.routingservice.application.out.repoport.OrderRepoPort;
import com.logistics.routingservice.application.out.repoport.WarehouseRepoPort;
import com.logistics.routingservice.domain.Order;
import com.logistics.routingservice.domain.OrderStatus;
import com.logistics.routingservice.domain.Route;
import com.logistics.routingservice.domain.Warehouse;
import com.logistics.routingservice.infrastructure.execeptions.error.NoCredentialsException;
import com.logistics.routingservice.infrastructure.execeptions.error.OrderAlreadyDeliveredException;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ManagerService implements ManageOrderUseCase, WarehouseUseCase, AuthorizationUseCase {

    private final OrderRepoPort orderRepository;
    private final WarehouseRepoPort warehouseRepository;

    @Override
    public Integer getCapacity(String orderId){
        Order order = orderRepository.getOrderByOrderId(orderId);
        return order.getWeight();
    }

    @Override
    public void validRole(String role){
        if (!(role.equals("ADMIN") || role.equals("MANAGER"))) {
            throw new NoCredentialsException("You do not have permission to access this resource!");
        }
    }

    @Override
    public void createNewOrder(Route route, double capacity, String userId, String email) {
        Order order = Order.builder()
                .warehouseId(route.getWarehouse().getWarehouseId())
                .userId(userId)
                .userEmail(email)
                .createTime(new Date())
                .address(route.getWarehouse().getAddress())
                .distance(route.getDistanceMeters())
                .weight((int) capacity)
                .type(route.getWarehouse().getType())
                .price(route.getPriceUah())
                .status(OrderStatus.CREATED)
                .build();
        orderRepository.saveOrder(order);
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.deleteOrder(orderId);
    }

    @Override
    public void setDeliveryTime(String orderId, Date deliveryTime) {
        Order order = findOrderById(orderId);
        order.setDeliveryTime(deliveryTime);
        orderRepository.saveOrder(order);
    }


    @Override
    public void changeOrderStatus(String orderId, OrderStatus orderStatus) {
        Order order = findOrderById(orderId);
        idempotentStatusOrder(order, orderStatus);
        order.setStatus(orderStatus);
        orderRepository.saveOrder(order);
    }

    @Override
    public List<Order> getOrdersForUser(String userId) {
        return orderRepository.getOrdersByUserId(userId);
    }

    @Override
    public List<Order> getAllOrders(){
        return orderRepository.getAllOrders();
    }

    @Override
    public List<Warehouse> getWarehouses(){
        return warehouseRepository.getAllWarehouses();
    }

    private Order findOrderById(String orderId) {
        return orderRepository.getOrderByOrderId(orderId);
    }

    private void idempotentStatusOrder(Order order, OrderStatus orderStatus) {
        if (order.getStatus().equals(orderStatus)) {
            throw new OrderAlreadyDeliveredException("The order already has this status!");
        }
    }

}
