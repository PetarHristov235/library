package com.library.impl.service;

import com.library.impl.db.entity.OrderEntity;

import java.util.List;

public interface OrderService {

        void saveOrder(OrderEntity order);
        void deleteOrderById(Long id);
        OrderEntity findOrderById(Long id);
        List<OrderEntity> findAllOrders();
        List<OrderEntity> getOrdersByUsername(String username);
}
