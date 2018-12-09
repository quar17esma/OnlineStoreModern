package com.quar17esma.service.impl;

import com.quar17esma.service.PagingAndSortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractPagingAndSortingService<T> extends AbstractCRUDService<T> implements PagingAndSortingService<T> {

    @Autowired
    private JpaRepository<T, Long> repository;

    @Override
    public Iterable<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
