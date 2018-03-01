package com.quar17esma.dao;

import com.quar17esma.model.Good;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodRepository extends JpaRepository<Good, Long> {

    @Cacheable(value = "goodsCache")
    List<Good> findAll();

    @Cacheable(value = "goodsCache")
    Good findOne(Long goodId);

    @CachePut(value="goodsCache", key="#result.id")
    Good save(Good good);

    @CacheEvict("goodsCache")
    void delete(Long goodId);
}
