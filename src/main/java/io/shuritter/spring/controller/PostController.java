package io.shuritter.spring.controller;

import io.shuritter.spring.model.Post;
import org.springframework.http.ResponseEntity;

public interface PostController extends BaseController<Post> {
    ResponseEntity<Post> add(String id, Post post);
    ResponseEntity<Post> update(String userId, String id, Post post);
    ResponseEntity<Post> delete(String userId, String id);
}