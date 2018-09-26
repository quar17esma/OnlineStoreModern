package com.quar17esma.service.impl;

import com.quar17esma.dao.GoodRepository;
import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.service.GoodService;
import com.quar17esma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("goodService")
@Transactional
public class GoodServiceImpl extends AbstractCRUDService<Good> implements GoodService {
    @Autowired
    private GoodRepository repository;
    @Autowired
    private OrderService orderService;

    @Override
    public void addGoodToOrder(Order order, Long goodId, int orderedQuantity) {
        Good good = repository.findOne(goodId);
        writeOffGood(orderedQuantity, good);
        
        if (order.getOrderedGoods().containsKey(good)){
            int orderedBefore = order.getOrderedGoods().get(good);
            order.getOrderedGoods().put(good, orderedBefore + orderedQuantity);
        } else {
            order.getOrderedGoods().put(good, orderedQuantity);
        }

        orderService.save(order);
    }

    private void writeOffGood(int orderedQuantity, Good good) {
        int diff = good.getQuantity() - orderedQuantity;
        if (diff >= 0) {
            good.setQuantity(diff);
        } else {
            throw new RuntimeException("There is not enough good quantity");
        }
    }
}
