package com.codegym.repository;

import java.util.List;

public interface ICustomerRepository<T> {
    List<T> findAll();

    T findById(long id);

    void save(T model);

    void remove(long id);
}