package com.example.lab2.dao.impl.inmem;

import com.example.lab2.dao.AbstractDao;

import java.util.Collection;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class InMemoryAbstractDao<T> implements AbstractDao<T> {
    protected Map<Long, T> entities;
    protected Function<T, Long> idGetter;
    protected BiConsumer<T, Long> idSetter;
    protected InMemoryDatabase database;

    InMemoryAbstractDao(Map<Long, T> entities, Function<T, Long> idGetter, BiConsumer<T, Long> idSetter,
                        InMemoryDatabase database) {
        this.entities = entities;
        this.idGetter = idGetter;
        this.idSetter = idSetter;
        this.database = database;
    }

    @Override
    public T get(Long id) {
        return entities.get(id);
    }

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public void insert(T entity, boolean generatedId) {
        if (generatedId) {
            long maxId = entities.keySet().stream()
                    .mapToLong(Long::longValue)
                    .max()
                    .orElse(0);
            idSetter.accept(entity, maxId + 1);
        }
        entities.put(idGetter.apply(entity), entity);

    }

    @Override
    public void delete(T entity) {
        entities.remove(idGetter.apply(entity));
    }

    @Override
    public void update(T entity) {
        entities.put(idGetter.apply(entity), entity);
    }
}
