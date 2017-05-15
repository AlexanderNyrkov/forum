package io.shuritter.spring.service;

import io.shuritter.spring.model.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    List<T> getAll();
    void add(T entity);
    void update(T entity);
    void delete(String id);
    T getById(String id);

}
