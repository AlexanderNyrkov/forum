package io.shuritter.spring.controller;

import io.shuritter.spring.model.BaseEntity;
import io.shuritter.spring.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<T extends BaseEntity> {
    ResponseEntity<List<T>> list();
    ResponseEntity<T> add(T entity);
    ResponseEntity<T> update(String id, T entity);
    ResponseEntity<T> delete(String id);
    ResponseEntity<T> getById(String id, T entity);
}
