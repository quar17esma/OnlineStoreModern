package com.quar17esma.service.impl;

import com.quar17esma.dao.GoodRepository;
import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class GoodService implements IGoodService {

    @Autowired
    private GoodRepository repository;

    @Override
    public List<Good> findAll() {
        return repository.findAll();
    }

    @Override
    public int countAll() {
        return repository.countAll();
    }

    @Override
    public Good findById(Long goodId) {
        Optional<Good> good = repository.findById(goodId);
        if (good.isPresent()) {
            return good.get();

        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteById(Long goodId) {
        repository.deleteById(goodId);
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
