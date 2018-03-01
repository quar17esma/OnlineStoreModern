package com.quar17esma.dao;

import com.quar17esma.model.Order;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Cacheable(value = "ordersCache", key = "#userId")
    List<Order> findAllByUserId(Long userId);

    @Cacheable(value = "ordersCache")
    List<Order> findAll();

    @Cacheable(value = "ordersCache")
    Order findOne(Long orderId);

    @CachePut(value="ordersCache", key="#result.id")
    Order save(Order order);

    @CacheEvict("ordersCache")
    void delete(Long orderId);
}
