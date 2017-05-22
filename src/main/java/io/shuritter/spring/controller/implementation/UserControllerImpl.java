package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.UserController;
import io.shuritter.spring.model.User;
import io.shuritter.spring.model.response.Response;
import io.shuritter.spring.model.response.ResponseMany;
import io.shuritter.spring.model.response.ResponseOne;
import io.shuritter.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

import static io.shuritter.spring.model.response.Status.ERROR;
import static io.shuritter.spring.model.response.Status.SUCCESS;

/**
 * Controller for user requests
 * Extends of {@link BaseControllerImpl}
 * Implementation of {@link UserController}
 * @author Alexander Nyrkov
 */
@RestController
@RequestMapping(value = "/api/v1/")
public class UserControllerImpl extends BaseControllerImpl<User> implements UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
    private UserService service;

    @Inject
    @Qualifier("userService")
    public void setService(UserService service) {
        this.service = service;
    }

    /**
     * Insert data the User table
     * You must send a request in the JSON format
     * @param user New user
     * @return HTTP Status 201(CREATED) if user is created
     */
    @PostMapping(value = "users", consumes = "application/json")
    public ResponseEntity<User> add(@RequestBody User user) {
        service.add(user);
        logger.info("User added");
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
    }

    /**
     * Get in the JSON format all users from User table
     * @return Response with total users, limit, skip, data, status SUCCESS and HTTP Status 200(OK)
     * if no request errors
     */
    @GetMapping(value="users", produces = "application/json")
    public ResponseEntity<Response> getAll() {
        List<User> users = this.service.getAll(false);
        ResponseMany<User> response = new ResponseMany<>();
        response.setTotal(users.size());
        response.setLimit(response.getTotal());
        response.setSkip(0);
        response.setData(users);
        response.setStatus(SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get in the JSON format user with wanted id from User table
     * @param id The id for find user
     * @return Response with user data with status SUCCESS and HTTP Status 200(OK) if user will successfully find
     * and status ERROR with HTTP Status 404(NOT FOUND) if user is deleted or id not found
     */
    @GetMapping(value = "users/{id}", produces = "application/json")
    public ResponseEntity<Response> getById(@PathVariable("id") String id) {
        ResponseOne<User> response = new ResponseOne<>();
        if (service.getById(id) == null) {
            response.setStatus(ERROR);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.setData(service.getById(id));
        response.setStatus(SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Logically delete the user in the table
     * @param id The id of the user you want to delete
     * @return HTTP Status 200(OK) if user is successfully deleted
     * and HTTP Status 404(NOT FOUND) if user is already deleted or id not found
     */
    @DeleteMapping(value = "users/{id}", consumes = "application/json")
    public ResponseEntity<User> delete(@PathVariable("id") String id) {
        if (service.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        logger.info("User removed");
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Updates user data in a table
     * You must send a request in the JSON format
     * @param id The user id
     * @param user The user to update
     * @return HTTP Status 200(OK) if user is successfully updated
     * and HTTP Status 404(NOT FOUND) if user is already deleted or id not found
     */
    @PutMapping(value = "users/{id}", consumes = "application/json")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody User user) {
        if (service.getById(id) == null) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        service.update(id, user);
        logger.info("User updated");
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}

