package io.shuritter.spring.service;

import io.shuritter.spring.model.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    List<T> getAll(Boolean showDeleted);
    void update(String id, T updated);
    void delete(String id);
    T getById(String id);

}
