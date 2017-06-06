package io.shuritter.spring.controller;

import io.shuritter.spring.model.User;
import io.shuritter.spring.model.response.Response;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface that contains methods to control UserControllerImpl class
 * Extends {@link BaseController}
 * @author Alexander Nyrkov
 */
public interface UserController extends BaseController<User> {
    ResponseEntity<User> add(User user);
    ResponseEntity<User> update(String id, User user, HttpServletRequest request);
    ResponseEntity<User> delete(String id, HttpServletRequest request);
    ResponseEntity<Response> getById(String id, HttpServletRequest request);
}
