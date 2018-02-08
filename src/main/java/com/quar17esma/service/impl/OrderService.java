package com.quar17esma.service.impl;

import com.quar17esma.dao.OrderRepository;
import com.quar17esma.enums.OrderStatus;
import com.quar17esma.model.Order;
import com.quar17esma.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository repository;

    @Override
    public List<Order> findAllByClientId(Long clientId) {
        return repository.findAllByClientId(clientId);
    }

    @Override
    @Transactional
    public boolean payOrder(Long orderId) {
        boolean result = false;

        Optional<Order> order = repository.findById(orderId);
        if (order.isPresent()) {
            order.get().setStatus(OrderStatus.PAID);
            repository.save(order.get());

            result = true;
        }

        return result;
    }

    @Override
    @Transactional
    public void sendOrder(Order order) {
            order.setOrderedAt(LocalDateTime.now());
            repository.save(order);
    }
}
