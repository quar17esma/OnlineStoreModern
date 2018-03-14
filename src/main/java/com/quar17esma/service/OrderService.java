package com.quar17esma.service;

import com.quar17esma.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByUserIdFetchOrderedGoods(Long userId);

    void confirmOrder(Long orderId);

    void saveOrder(Order order);

    void payOrder(Long orderId);
}
