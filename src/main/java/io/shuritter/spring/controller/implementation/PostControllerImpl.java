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
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static io.shuritter.spring.model.response.Status.ERROR;
import static io.shuritter.spring.model.response.Status.SUCCESS;
import static org.springframework.http.HttpStatus.*;
/**
 * Controller that implements PostController related methods
 * Extends {@link BaseControllerImpl}
 * Implements {@link PostController}
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
     * Checks whether these models are exist or not
     * @param userId identifier of user
     * @param id identifier of post
     * @return true if models exist and false if they doesn't
     */
    private Boolean isNull(String userId, String id) {
        return userService.getById(userId) == null || service.getById(id) == null;
    }

    /**
     * Create new post
     * Request must be in JSON format
     * @param id post author id
     * @param post new post
     * @return HTTP Status 201(CREATED) if user is created
     * and HTTP Status 404(NOT FOUND) if user deleted or not exist
     */
    @PostMapping(value = "users/{id}/posts", consumes = "application/json")
    public ResponseEntity<Post> add(@PathVariable("id") String id, @RequestBody Post post, HttpServletRequest request) {

        if (userService.getById(id) == null) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        if (authorization(request)) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        if (loginPassword(request, userService.getById(id))) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        service.add(post, userService.getById(id));
        logger.info("Post added");
        return new ResponseEntity<>(new HttpHeaders(), CREATED);
    }

    /**
     * Return all existing posts
     * @return HTTP Status 200(OK), pagination data and posts list
     */
    @GetMapping(value = "posts", produces = "application/json")
    public ResponseEntity<Response> getAll(HttpServletRequest request) {
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
     * Return post with specified ID
     * @param id identifier of requested post
     * @return HTTP Status 200(OK) and Post model or HTTP Status 404(NOT FOUND) if user/post is deleted or not found
     */
    @GetMapping(value = "users/{userId}/posts/{id}", produces = "application/json")
    public ResponseEntity<Response> getById(@PathVariable("userId") String userId, @PathVariable("id") String id, HttpServletRequest request) {
        ResponseOne<Post> response = new ResponseOne<>();
        if (isNull(userId,id)) {
            response.setStatus(ERROR);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (authorization(request)) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        if (loginPassword(request, userService.getById(userId))) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        response.setData(service.getById(id));
        response.setStatus(SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates post
     * Request must be in JSON format
     * @param userId identifier of user
     * @param id identifier of post
     * @param post post to be updated
     * @return HTTP Status 200(OK) if post is successfully updated
     * and HTTP Status 404(NOT FOUND) if user/post is deleted or id not found
     */
    @PutMapping(value = "users/{userId}/posts/{id}", consumes = "application/json")
    public ResponseEntity<Post> update(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody Post post, HttpServletRequest request) {
        if (isNull(userId, id)) {
            return new ResponseEntity<>(new HttpHeaders(), NOT_FOUND);
        }

        if (authorization(request)) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        if (loginPassword(request, userService.getById(userId))) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        service.update(id, post);
        logger.info("Post updated");
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }

    /**
     * Delete post
     * @param userId post author id
     * @param id identifier of a post to be deleted
     * @return HTTP Status 200(OK) if post is successfully deleted
     * and HTTP Status 404(NOT FOUND) if user/post is already deleted or id not found
     */
    @DeleteMapping(value = "users/{userId}/posts/{id}", consumes = "application/json")
    public ResponseEntity<Post> delete(@PathVariable("userId") String userId, @PathVariable("id") String id, HttpServletRequest request) {
        if (isNull(userId, id)) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        if (authorization(request)) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        if (loginPassword(request, userService.getById(userId))) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        service.delete(id);
        logger.info("Post with");
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }
}

