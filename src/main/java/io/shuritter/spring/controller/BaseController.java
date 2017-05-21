package io.shuritter.spring.controller;

import io.shuritter.spring.model.BaseEntity;
import io.shuritter.spring.model.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<T extends BaseEntity> {
    ResponseEntity<Response> getAll();
}
