package com.quar17esma.service;

import java.util.List;

public interface CRUDService<T> {
    List<T> findAll();

    T findById(Long id);

    void delete(Long id);

    void delete(T item);

    void save(T item);
}
