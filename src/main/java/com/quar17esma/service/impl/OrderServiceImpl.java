package com.quar17esma.service.impl;

import com.quar17esma.dao.OrderRepository;
import com.quar17esma.enums.OrderStatus;
import com.quar17esma.model.Order;
import com.quar17esma.service.OrderService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("orderService")
@Transactional
public class OrderServiceImpl extends AbstractCRUDService<Order> implements OrderService {
    @Autowired
    private OrderRepository repository;

    @Override
    public List<Order> findAllByUserId(Long userId) {
        return repository.findAllByUserIdOrderByOrderedAtDesc(userId);
    }

    @Override
    public List<Order> findAllByUserIdFetchOrderedGoods(Long userId) {
        List<Order> orders = repository.findAllByUserIdOrderByOrderedAtDesc(userId);
        for (Order order : orders) {
            Hibernate.initialize(order.getOrderedGoods());
        }

        return orders;
    }

    @Override
    @Transactional
    public void confirmOrder(Long id) {
        Optional<Order> order = Optional.ofNullable(repository.findOne(id));
        if (order.isPresent() && order.get().getStatus() == OrderStatus.NEW) {
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
            order.setOrderedAt(LocalDateTime.now());
        }
        repository.save(order);
    }

    @Override
    public void payOrder(Long id) {
        Optional<Order> order = Optional.ofNullable(repository.findOne(id));
        if (order.isPresent()) {
            order.get().setStatus(OrderStatus.PAID);
            repository.save(order.get());
        } else {
            throw new RuntimeException("Order not found. Payment failed");
        }
    }
}
