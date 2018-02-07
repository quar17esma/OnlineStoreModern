package com.quar17esma.service;

import com.quar17esma.model.Order;

import java.util.List;

public interface IOrdersService {

    List<Order> getOrdersByClientId(int clientId);

    boolean payOrder(int orderId);

    void sendOrder(Order order);
}
