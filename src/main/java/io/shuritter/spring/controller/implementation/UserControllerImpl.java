package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.BaseController;
import io.shuritter.spring.controller.UserController;
import io.shuritter.spring.model.User;
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

@RestController
public class UserControllerImpl extends BaseControllerImpl<User> implements BaseController<User>, UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
    private UserService service;

    @Inject
    @Qualifier("userService")
    public void setService(UserService service) {
        this.service = service;
    }

    @PostMapping(value = "/users", consumes = "application/json")
    public ResponseEntity<User> add(@RequestBody User user) {
        service.add(user);
        logger.info("User added");
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = this.service.getAll();
        if (users.size() == 0) {
            logger.error("No users");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info(this.service.getAll().toString());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<User> getById(@PathVariable("id") String id) {
        User user = service.getById(id);
        if (checkDeleted(id) || checkEmpty(id)) {
            logger.info("User with " + id + " id not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("User with " + id + " id: " + user);
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }


    @DeleteMapping(value = "/users/{id}", consumes = "application/json")
    public ResponseEntity<User> delete(@PathVariable("id") String id) {
        if (checkEmpty(id) || checkDeleted(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        logger.info("User removed");
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}", consumes = "application/json")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody User user) {
        if (checkDeleted(id) || checkEmpty(id)) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        service.update(user, id);
        logger.info("User updated");
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    public Boolean checkEmpty(String userId) {
        return service.getById(userId) == null;
    }

    public Boolean checkDeleted(String userId) {
        return service.getById(userId).isDeleted();
    }

}

