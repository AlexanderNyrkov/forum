package io.shuritter.spring.controller;

import io.shuritter.spring.model.User;
import io.shuritter.spring.model.response.Response;
import org.springframework.http.ResponseEntity;

/**
 * Interface whose methods are to be implemented in UserControllerImpl class
 * Extends of {@link BaseController}
 * @author Alexander Nyrkov
 */
public interface UserController extends BaseController<User> {
    ResponseEntity<User> add(User user);
    ResponseEntity<User> update(String id, User user);
    ResponseEntity<User> delete(String id);
    ResponseEntity<Response> getById(String id);
}
