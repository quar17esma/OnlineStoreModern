package com.quar17esma.dao;

import com.quar17esma.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long> {
//    Integer countAll();
}
