package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.PostController;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.response.Response;
import io.shuritter.spring.model.response.ResponseMany;
import io.shuritter.spring.model.response.ResponseOne;
import io.shuritter.spring.service.PostService;
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
import static org.springframework.http.HttpStatus.*;
/**
 * Controller for post requests
 * Extends of {@link BaseControllerImpl}
 * Implementation of {@link PostController}
 * @author Alexander Nyrkov
 */
@RestController
@RequestMapping("/api/v1/")
public class PostControllerImpl extends BaseControllerImpl<Post> implements PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostControllerImpl.class);
    private PostService service;
    private UserService userService;

    @Inject
    @Qualifier("postService")
    public void setService(PostService service) {
        this.service = service;
    }

    @Inject
    @Qualifier("userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    /**
     * Checks the identifier for existence
     * @param userId The user id
     * @param id The post Id
     * @return true if the identifier exists and false if there is no
     */
    private Boolean isNull(String userId, String id) {
        return userService.getById(userId) == null || service.getById(id) == null || service.getById(id).getUserId() != userService.getById(userId);
    }

    /**
     * Insert data the User table
     * You must send a request in the JSON format
     * @param id Post author id
     * @param post New post
     * @return HTTP Status 201(CREATED) if user is created
     * and HTTP Status 404(NOT FOUND) if user id deleted or not exist
     */
    @PostMapping(value = "users/{id}/posts", consumes = "application/json")
    public ResponseEntity<Post> add(@PathVariable("id") String id, @RequestBody Post post) {
        if (userService.getById(id) == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        service.add(post, userService.getById(id));
        logger.info("Post added");
        return new ResponseEntity<>(new HttpHeaders(), CREATED);
    }

    /**
     * Get in the JSON format all posts from Post table
     * @return Response with total posts, limit, skip, data, status SUCCESS and HTTP Status 200(OK)
     * if no request errors
     */
    @GetMapping(value = "posts", produces = "application/json")
    public ResponseEntity<Response> getAll() {
        List<Post> posts = this.service.getAll(false);
        ResponseMany<Post> response = new ResponseMany<>();
        response.setTotal(posts.size());
        response.setLimit(response.getTotal());
        response.setSkip(0);
        response.setData(posts);
        response.setStatus(SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get in the JSON format post with wanted id from Post table
     * @param id The id for find post
     * @return Response with post data, status SUCCESS and HTTP Status 200(OK) if post will successfully find
     * and status ERROR with HTTP Status 404(NOT FOUND) if user/post is deleted or id not found
     */
    @GetMapping(value = "users/{userId}/posts/{id}", produces = "application/json")
    public ResponseEntity<Response> getById(@PathVariable("userId") String userId, @PathVariable("id") String id) {
        ResponseOne<Post> response = new ResponseOne<>();
        if (isNull(userId,id)) {
            response.setStatus(ERROR);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.setData(service.getById(id));
        response.setStatus(SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates post data in a table
     * You must send a request in the JSON format
     * @param userId The user id
     * @param id The post id
     * @param post The post to update
     * @return HTTP Status 200(OK) if post is successfully updated
     * and HTTP Status 404(NOT FOUND) if user/post is already deleted or id not found
     */
    @PutMapping(value = "users/{userId}/posts/{id}", consumes = "application/json")
    public ResponseEntity<Post> update(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody Post post) {
        if (isNull(userId, id)) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        service.update(id, post);
        logger.info("Post updated");
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }

    /**
     * Logically delete the post in the table
     * @param userId Post author id
     * @param id The id of the post you want to delete
     * @return HTTP Status 200(OK) if post is successfully deleted
     * and HTTP Status 404(NOT FOUND) if user/post is already deleted or id not found
     */
    @DeleteMapping(value = "users/{userId}/posts/{id}", consumes = "application/json")
    public ResponseEntity<Post> delete(@PathVariable("userId") String userId, @PathVariable("id") String id) {
        if (isNull(userId, id)) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        service.delete(id);
        logger.info("Post with");
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }
}

