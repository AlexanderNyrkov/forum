package io.shuritter.spring.service;

import io.shuritter.spring.model.BaseEntity;

import java.util.List;

/**
 * Interface whose methods are to be implemented in Service classes
 * @param <T> Describes any entity class
 * @author Alexander Nyrkov
 */
public interface BaseService<T extends BaseEntity> {
    List<T> getAll(Boolean showDeleted);
    void update(String id, T updated);
    void delete(String id);
    T getById(String id);
}
