package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.BaseController;
import io.shuritter.spring.controller.UserController;
import io.shuritter.spring.model.response.Response;
import io.shuritter.spring.model.response.ResponseMany;
import io.shuritter.spring.model.User;
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

import static io.shuritter.spring.model.response.Status.SUCCESS;

@RestController
public class UserControllerImpl extends BaseControllerImpl<User> implements BaseController<User>, UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
    private UserService service;

    @Inject
    @Qualifier("userService")
    public void setService(UserService service) {
        this.service = service;
    }

    @GetMapping(value="/api/v1/users", produces = "application/json")
    public ResponseEntity<Response> getAll() {
        List<User> users = this.service.getAll();
        ResponseMany<User> response = new ResponseMany<>();
        response.setTotal(users.size());
        response.setLimit(response.getTotal());
        response.setSkip(0);
        response.setData(users);
        response.setStatus(SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/users", consumes = "application/json")
    public ResponseEntity<User> add(@RequestBody User user) {
        service.add(user);
        logger.info("User added");
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<Response> getById(@PathVariable("id") String id) {
        ResponseOne<User> response = new ResponseOne<>();
        response.setData(service.getById(id));
        response.setStatus(SUCCESS);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping(value = "/users/{id}", consumes = "application/json")
    public ResponseEntity<User> delete(@PathVariable("id") String id) {
        if (deleted(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        logger.info("User removed");
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}", consumes = "application/json")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody User user) {
        if (deleted(id)) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        service.update(user, id);
        logger.info("User updated");
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    public Boolean deleted(String userId) {
        return service.getById(userId).isDeleted();
    }

}

