package io.shuritter.spring.controller;

import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.response.Response;
import org.springframework.http.ResponseEntity;

/**
 * Interface whose methods are to be implemented in CommentControllerImpl class
 * Extends of {@link BaseController}
 * @author Alexander Nyrkov
 */
public interface CommentController extends BaseController<Comment> {
    ResponseEntity<Comment> add(String userId, String postId, Comment comment);
    ResponseEntity<Comment> update(String userId, String postId, String id, Comment comment);
    ResponseEntity<Comment> delete(String userId, String postId, String id);
    ResponseEntity<Response> getById(String userId, String postId, String id);
}