package com.quar17esma.service.impl;

import com.quar17esma.dao.OrderRepository;
import com.quar17esma.enums.OrderStatus;
import com.quar17esma.model.Order;
import com.quar17esma.service.OrderService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repository;

    @Override
    public List<Order> findAllByClientId(Long clientId) {
        return repository.findAllByUserId(clientId);
    }

    @Override
    public List<Order> findAllByClientIdFetchOrderedGoods(Long clientId) {
        List<Order> orders = repository.findAllByUserId(clientId);
        for (Order order:orders) {
            Hibernate.initialize(order.getOrderedGoods());
        }
        return orders;
    }

    @Override
    @Transactional
    public void confirmOrder(Long orderId) {

        Optional<Order> order = Optional.ofNullable(repository.findOne(orderId));
        if (order.isPresent()) {
            order.get().setStatus(OrderStatus.CONFIRMED);
            repository.save(order.get());
        } else {
            throw new RuntimeException("Order not found. Confirmation failed");
        }
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        if (order.getOrderedAt() == null) {
            order.setOrderedAt(new Date());
        }
        repository.save(order);
    }
}
