package com.quar17esma.service;

import com.quar17esma.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllByClientId(Long clientId);

    List<Order> findAllByUserIdFetchOrderedGoods(Long clientId);

    void confirmOrder(Long orderId);

    void saveOrder(Order order);

    void payOrder(Long orderId);
}
