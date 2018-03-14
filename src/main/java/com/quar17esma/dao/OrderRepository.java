package com.quar17esma.dao;

import com.quar17esma.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserIdOrderByOrderedAtDesc(Long userId);

}
