package com.quar17esma.service;

import com.quar17esma.model.Good;
import com.quar17esma.model.Order;

public interface GoodService extends PagingAndSortingService<Good> {

    void addGoodToCart(Order cart, Long goodId, int orderedQuantity);
}
