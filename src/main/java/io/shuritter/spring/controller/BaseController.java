package io.shuritter.spring.controller;

import io.shuritter.spring.model.BaseEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<T extends BaseEntity> {
    ResponseEntity<List<T>> getAll();
    ResponseEntity<T> add(T entity);
    ResponseEntity<T> update(String id, T entity);
    ResponseEntity<T> delete(String id);
    ResponseEntity<T> getById(String id);
}
