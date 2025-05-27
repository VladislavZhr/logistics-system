package com.logistics.routingservice.infrastructure.adapter.out.database.adapter;

import com.logistics.routingservice.application.out.repoport.OrderRepoPort;
import com.logistics.routingservice.domain.Order;
import com.logistics.routingservice.infrastructure.adapter.out.database.entity.OrderEntity;
import com.logistics.routingservice.infrastructure.adapter.out.database.mapper.OrderMapper;
import com.logistics.routingservice.infrastructure.adapter.out.database.repository.OrderRepository;
import com.logistics.routingservice.infrastructure.execeptions.error.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderRepoAdapter implements OrderRepoPort {

    private final OrderRepository orderRepository;

    @Override
    public void deleteByUserId(String userId) {
        orderRepository.deleteByUserId(userId);
    }


    @Override
    public void saveOrder(Order order){
        orderRepository.save(OrderMapper.INSTANCE.toEntity(order));
    }

    @Override
    public void deleteOrder(String orderId){
        orderRepository.delete(findOrderById(orderId));

    }

    @Override
    public Order getOrderByOrderId(String orderId){
        return OrderMapper.INSTANCE.toDomain(findOrderById(orderId));
    }

    private OrderEntity findOrderById(String orderId){
        return orderRepository.findByOrderId(Long.parseLong(orderId))
                .orElseThrow(()-> new OrderNotFoundException("Order with ID " + orderId + " was not found"));
    }

    @Override
    public List<Order> getOrdersByUserId(String userId){
        return orderRepository.findByUserId(userId).stream()
                .map(OrderMapper.INSTANCE::toDomain)
                .toList();
    }

    @Override
    public List<Order> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(OrderMapper.INSTANCE::toDomain)
                .toList();
    }
}
