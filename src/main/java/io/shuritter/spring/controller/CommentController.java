package io.shuritter.spring.controller;

import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.response.Response;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface that contains methods to control CommentControllerImpl class
 * Extends {@link BaseController}
 * @author Alexander Nyrkov
 */
public interface CommentController extends BaseController<Comment> {
    ResponseEntity<Comment> add(String userId, String postId, Comment comment, HttpServletRequest request);
    ResponseEntity<Comment> update(String userId, String postId, String id, Comment comment, HttpServletRequest request);
    ResponseEntity<Comment> delete(String userId, String postId, String id, HttpServletRequest request);
    ResponseEntity<Response> getById(String userId, String postId, String id, HttpServletRequest request);
}