package com.quar17esma.service;

import com.quar17esma.model.Good;
import com.quar17esma.model.Order;

import java.util.List;

public interface GoodService extends PagingAndSortingService<Good> {

    void addGoodToCart(Order cart, Long goodId, int orderedQuantity);

    List<Good> findByNameContains(String searchString);
}
