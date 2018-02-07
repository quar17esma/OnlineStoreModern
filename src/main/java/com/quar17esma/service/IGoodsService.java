package com.quar17esma.service;

import com.quar17esma.model.Good;
import com.quar17esma.model.Order;

import java.util.List;

public interface IGoodsService {

    List<Good> getAllGoods();

    List<Good> getGoodsByPage(int page, int goodsOnPage);

    int getAllGoodsQuantity();

    Good getGoodById(int goodId);

    void deleteGoodById(int goodId);

    void addGood(Good good);

    void updateGood(Good good);

    void addGoodToOrder(Order order, int goodId, int orderedQuantity);
}
