package com.example.lab2.dao.impl;

import com.example.lab2.dao.AbstractDao;
import jakarta.persistence.*;
import java.util.Collection;

public abstract class AbstractDaoImpl<T> implements AbstractDao<T> {
    @PersistenceContext(unitName = "LabPU")
    protected EntityManager em;
    private final Class<T> clazz;

    protected AbstractDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T get(Long id) {
        return em.find(clazz, id);
    }

    @Override
    public Collection<T> findAll() {
        return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz)
                .getResultList();
    }

    @Override
    public void insert(T entity, boolean generatedId) {
        em.persist(entity);
    }

    @Override
    public void update(T entity) {
        em.merge(entity);
    }

    @Override
    public void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }
}
