package io.shuritter.spring.controller;

import io.shuritter.spring.model.BaseEntity;
import io.shuritter.spring.model.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<T extends BaseEntity> {
    ResponseEntity<Response> getAll();
    ResponseEntity<T> add(T entity);
    ResponseEntity<T> update(String id, T entity);
    ResponseEntity<T> delete(String id);
    ResponseEntity<Response> getById(String id);
}
