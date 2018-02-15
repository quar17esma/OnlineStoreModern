package com.quar17esma.service;

import com.quar17esma.model.Good;
import com.quar17esma.model.Order;

import java.util.List;

public interface GoodService {

    List<Good> findAll();

    int countAll();

    Good findById(Long goodId);

    void deleteById(Long goodId);

    void save(Good good);

    void addGoodToOrder(Order order, Long goodId, int orderedQuantity);
}
