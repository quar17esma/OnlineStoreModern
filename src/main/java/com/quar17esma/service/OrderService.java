package com.quar17esma.service;

import com.quar17esma.model.Order;

import java.util.List;

public interface OrderService extends CRUDService<Order> {
    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByUserIdFetchOrderedGoods(Long userId);

    void confirmOrder(Long id);

    void saveOrder(Order order);

    void payOrder(Long id);
}
