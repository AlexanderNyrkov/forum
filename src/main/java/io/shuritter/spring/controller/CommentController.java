package io.shuritter.spring.controller;

import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.response.Response;
import org.springframework.http.ResponseEntity;

public interface CommentController extends BaseController<Comment> {
    ResponseEntity<Response> getAll(String userId, String postId);
    ResponseEntity<Comment> add(String userId, String postId, Comment comment);
    ResponseEntity<Comment> update(String userId, String postId, String id, Comment comment);
    ResponseEntity<Comment> delete(String userId, String postId, String id, Comment comment);
    ResponseEntity<Response> getById(String userId, String postId, String id);
}