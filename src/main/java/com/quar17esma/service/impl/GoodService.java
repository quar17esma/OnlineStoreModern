package com.quar17esma.service.impl;

import com.quar17esma.dao.GoodRepository;
import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("goodService")
@Transactional
public class GoodService implements IGoodService {

    @Autowired
    private GoodRepository repository;

    @Override
    public List<Good> findAll() {
        return repository.findAll();
    }

    @Override
    public int countAll() {
//        return repository.countAll();
        return 0;
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
        Good good = findById(goodId);
        good.setQuantity(orderedQuantity);

        order.getGoods().add(good);
    }
}
