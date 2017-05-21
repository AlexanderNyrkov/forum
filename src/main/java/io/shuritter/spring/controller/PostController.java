package io.shuritter.spring.controller;

import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.response.Response;
import org.springframework.http.ResponseEntity;

public interface PostController extends BaseController<Post> {
    ResponseEntity<Post> add(String id, Post post);
    ResponseEntity<Post> update(String userId, String id, Post post);
    ResponseEntity<Post> delete(String userId, String id);
    ResponseEntity<Response> getById(String userId, String id);
}