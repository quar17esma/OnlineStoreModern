package com.quar17esma.service;

import com.quar17esma.model.Good;
import com.quar17esma.model.Order;

public interface GoodService extends CRUDService<Good> {
    void addGoodToOrder(Order order, Long goodId, int orderedQuantity);
}
