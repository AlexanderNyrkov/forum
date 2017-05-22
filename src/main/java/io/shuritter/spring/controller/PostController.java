package io.shuritter.spring.controller;

import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.response.Response;
import org.springframework.http.ResponseEntity;

/**
 * Interface whose methods are to be implemented in PostControllerImpl class
 * Extends of {@link BaseController}
 * @author Alexander Nyrkov
 */
public interface PostController extends BaseController<Post> {
    ResponseEntity<Post> add(String id, Post post);
    ResponseEntity<Post> update(String userId, String id, Post post);
    ResponseEntity<Post> delete(String userId, String id);
    ResponseEntity<Response> getById(String userId, String id);
}