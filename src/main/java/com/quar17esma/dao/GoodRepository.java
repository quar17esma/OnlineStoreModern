package com.quar17esma.dao;

import com.quar17esma.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodRepository extends JpaRepository<Good, Long> {

    List<Good> findByNameContains(String searchString);
}
