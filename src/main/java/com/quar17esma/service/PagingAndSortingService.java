package com.quar17esma.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PagingAndSortingService<T> extends CRUDService<T> {

    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);
}
