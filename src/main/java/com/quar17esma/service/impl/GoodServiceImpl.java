package com.quar17esma.service.impl;

import com.quar17esma.dao.GoodRepository;
import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.service.GoodService;
import com.quar17esma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("goodService")
@Transactional
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodRepository repository;

    @Autowired
    private OrderService orderService;

    @Override
    public List<Good> findAll() {
        return repository.findAll();
    }

    @Override
    public Good findById(Long goodId) {
        Optional<Good> good = Optional.ofNullable(repository.findOne(goodId));
        if (good.isPresent()) {
            return good.get();

        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteById(Long goodId) {
        repository.delete(goodId);
    }

    @Override
    public void save(Good good) {
        repository.save(good);
    }

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

        orderService.saveOrder(order);
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
