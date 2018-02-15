package com.quar17esma.service;

import com.quar17esma.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllByClientId(Long clientId);

    boolean payOrder(Long orderId);

    void sendOrder(Order order);
}
