package com.example.lab2.dao;

import java.util.Collection;

public interface AbstractDao<T> {
    T get(Integer id);

    Collection<T> findAll();

    void insert(T entity, boolean generatedId);

    void delete(T entity);

    void update(T entity);
}
