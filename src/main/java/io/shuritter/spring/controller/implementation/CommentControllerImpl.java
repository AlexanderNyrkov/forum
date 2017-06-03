package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.CommentController;
import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.response.Response;
import io.shuritter.spring.model.response.ResponseMany;
import io.shuritter.spring.model.response.ResponseOne;
import io.shuritter.spring.service.CommentService;
import io.shuritter.spring.service.PostService;
import io.shuritter.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Controller for comment requests
 * Extends of {@link BaseControllerImpl}
 * Implementation of {@link CommentController}
 * @author Alexander Nyrkov
 */
@RestController
@RequestMapping("/api/v1/")
public class CommentControllerImpl extends BaseControllerImpl<Comment> implements CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentControllerImpl.class);
    private CommentService service;
    private UserService userService;
    private PostService postService;

    @Inject
    public void setService(CommentService service) {
        this.service = service;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    /**
     * Checks the identifier for existence
     * @param userId The user id
     * @param postId The post Id
     * @param id The comment id
     * @return true if the identifier exists and false if there is no
     */
    private Boolean isNull(String userId, String postId, String id) {
        return userService.getById(userId) == null || postService.getById(postId) == null
                || service.getById(id) == null;
    }

    /**
     * Insert data the Comment table
     * You must send a request in the JSON format
     * @param userId Comment author id
     * @param postId Post id to which the comment was written
     * @param comment New post
     * @return HTTP Status 201(CREATED) if comment is created
     * and HTTP Status 404(NOT FOUND) if user/post id deleted or not exist
     */
    @PostMapping(value = "users/{userId}/posts/{postId}/comments", consumes = "application/json")
    public ResponseEntity<Comment> add(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                       @RequestBody Comment comment, HttpServletRequest request) {
        if (userService.getById(userId) == null || postService.getById(postId) == null) {
            return new ResponseEntity<>(new HttpHeaders(), NOT_FOUND);
        }

        if (authorization(request)) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        if (loginPassword(request, userService.getById(userId))) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        service.add(comment, userService.getById(userId), postService.getById(postId));
        logger.info("Comment added");
        return new ResponseEntity<>(new HttpHeaders(), CREATED);
    }

    /**
     * Get in the JSON format all comment from Comment table
     * @return Response with total comments, limit, skip, data, status SUCCESS and HTTP Status 200(OK)
     * if no request errors
     */
    @GetMapping(value = "comments", produces = "application/json")
    @Override
    public ResponseEntity<Response> getAll(HttpServletRequest request) {
        List<Comment> comments = service.getAll(false);
        ResponseMany<Comment> response = new ResponseMany<>();
        response.setTotal(comments.size());
        response.setLimit(response.getTotal());
        response.setSkip(0);
        response.setData(comments);
        response.setStatus(SUCCESS);
        return new ResponseEntity<>(response, OK);
    }

    /**
     * Get in the JSON format comment with wanted id from Comment table
     * @param id The id for find comment
     * @return Response with comment data, status SUCCESS and HTTP Status 200(OK) if comment will successfully find
     * and status ERROR with HTTP Status 404(NOT FOUND) if user/post/comment is deleted or id not found
     */
    @GetMapping(value = "users/{userId}/posts/{postId}/comments/{id}", produces = "application/json")
    public ResponseEntity<Response> getById(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                            @PathVariable("id") String id, HttpServletRequest request) {
        ResponseOne<Comment> response = new ResponseOne<>();
        if (isNull(userId,postId,id)) {
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
     * Updates comment data in a table
     * You must send a request in the JSON format
     * @param userId Comment author id
     * @param postId Post id to which the comment was written
     * @param id The comment id
     * @param comment The comment to update
     * @return HTTP Status 200(OK) if comment is successfully updated
     * and HTTP Status 404(NOT FOUND) if user/post/comment is already deleted or id not found
     */
    @PutMapping(value = "users/{userId}/posts/{postId}/comments/{id}", consumes = "application/json")
    public ResponseEntity<Comment> update(@PathVariable("userId") String userId, @PathVariable("postId") String postId, @PathVariable("id") String id,
                                          @RequestBody Comment comment, HttpServletRequest request) {
        if (isNull(userId, postId, id)) {
            return new ResponseEntity<>(new HttpHeaders(), NOT_FOUND);
        }

        if (authorization(request)) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        if (loginPassword(request, userService.getById(userId))) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        service.update(id, comment);
        logger.info("Comment updated");
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }

    /**
     * Logically delete the comment in the table
     * @param userId Comment author id
     * @param postId Post id to which the comment was written
     * @param id The id of the comment you want to delete
     * @return HTTP Status 200(OK) if comment is successfully deleted
     * and HTTP Status 404(NOT FOUND) if user/post/comment is already deleted or id not found
     */
    @DeleteMapping(value = "users/{userId}/posts/{postId}/comments/{id}", consumes = "application/json")
    public ResponseEntity<Comment> delete(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                          @PathVariable("id") String id, HttpServletRequest request) {
        if (isNull(userId, postId, id)) {
            return new ResponseEntity<>(new HttpHeaders(), NOT_FOUND);
        }

        if (authorization(request)) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }

        if (loginPassword(request, userService.getById(userId))) {
            return new ResponseEntity<>(new HttpHeaders(), UNAUTHORIZED);
        }
        service.delete(id);
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }
}
