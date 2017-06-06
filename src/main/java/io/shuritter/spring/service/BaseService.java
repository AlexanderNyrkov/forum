package io.shuritter.spring.service;

import io.shuritter.spring.model.BaseEntity;

import java.util.List;

/**
 * Interface with basic service methods
 * @param <T> the type of entities
 * @author Alexander Nyrkov
 */
public interface BaseService<T extends BaseEntity> {
    List<T> getAll(Boolean showDeleted);
    void update(String id, T updated);
    void delete(String id);
    T getById(String id);
}
