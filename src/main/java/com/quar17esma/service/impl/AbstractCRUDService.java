package com.quar17esma.service.impl;

import com.quar17esma.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractCRUDService<T> implements CRUDService<T> {
    @Autowired
    private JpaRepository<T, Long> repository;

    public List<T> findAll() {
        return repository.findAll();
    }

    public T findById(Long id) {
        return repository.findOne(id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public void delete(T item) {
        repository.delete(item);
    }

    public void save(T item) {
        repository.save(item);
    }
}
