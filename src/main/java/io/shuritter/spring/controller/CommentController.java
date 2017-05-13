package io.shuritter.spring.controller;

import io.shuritter.spring.model.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentController extends BaseController<Comment> {
    ResponseEntity<List<Comment>> getAll(String userId, String postId);
    ResponseEntity<Comment> add(String userId, String postId, Comment comment);
    ResponseEntity<Comment> update(String userId, String postId, String id, Comment comment);
    ResponseEntity<Comment> delete(String userId, String postId, String id, Comment comment);
    ResponseEntity<Comment> getById(String userId, String postId, String id);
}