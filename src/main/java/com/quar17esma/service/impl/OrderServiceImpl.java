package com.quar17esma.service.impl;

import com.quar17esma.dao.OrderRepository;
import com.quar17esma.enums.OrderStatus;
import com.quar17esma.model.Order;
import com.quar17esma.service.OrderService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
            setOrderedAtIfNull(order.get());
            repository.save(order.get());
        } else {
            throw new RuntimeException("Order not found. Confirmation failed");
        }
    }

    @Override
    @Transactional
    public void confirmOrder(Order order) {
        if (order.getStatus() == OrderStatus.NEW) {
            order.setStatus(OrderStatus.CONFIRMED);
            setOrderedAtIfNull(order);
            repository.save(order);
        } else {
            throw new IllegalStateException("Can't confirm order, that hasn't status 'NEW'.");
        }
    }

    @Override
    @Transactional
    public void save(Order order) {
        setOrderedAtIfNull(order);
        repository.save(order);
    }

    private void setOrderedAtIfNull(Order order) {
        if (order.getOrderedAt() == null) {
            order.setOrderedAt(LocalDateTime.now());
        }
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

    @Override
    public void cancelOrder(Long orderId) throws EntityNotFoundException {
        Order order = repository.findOne(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.CANCELED);
            repository.save(order);
        } else {
            throw new EntityNotFoundException("Order with ID(" + orderId + ") not found. Failed to cancel order");
        }
    }
}
