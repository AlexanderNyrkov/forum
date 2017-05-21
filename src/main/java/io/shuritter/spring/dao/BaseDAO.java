package io.shuritter.spring.dao;

import io.shuritter.spring.model.BaseEntity;

import java.util.List;
public interface BaseDAO<T extends BaseEntity> {
    List<T> getAll(Boolean showDeleted);
    void add(T entity);
    void update(T id);
    void delete(String id);
    T getById(String id);
}