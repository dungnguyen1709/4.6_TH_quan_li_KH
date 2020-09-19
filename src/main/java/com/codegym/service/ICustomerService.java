package com.codegym.service;

import java.util.List;

public interface ICustomerService<T> {
    List<T> findAll();

    T findById(long id);

    void save(T model);

    void remove(long id);
}