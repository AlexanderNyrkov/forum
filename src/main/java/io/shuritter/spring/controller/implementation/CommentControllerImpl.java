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
 * Controller that implements PostController related methods
 * Extends {@link BaseControllerImpl}
 * Implements {@link CommentController}
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
     * Checks whether these models are exist or not
     * @param userId identifier of user
     * @param postId identifier of post
     * @param id identifier of comment
     * @return true if models exist and false if they doesn't
     */
    private Boolean isNull(String userId, String postId, String id) {
        return userService.getById(userId) == null || postService.getById(postId) == null
                || service.getById(id) == null;
    }

    /**
     * Create new comment
     * Request must be in JSON format
     * @param userId comment author id
     * @param postId post id to which the comment was written
     * @param comment new comment
     * @return HTTP Status 201(CREATED) if comment is created
     * and HTTP Status 404(NOT FOUND) if user/post deleted or doesn't exist
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
     * Return all existing comments
     * @return HTTP Status 200(OK), pagination data and comments list
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
     * Return comment with specified ID
     * @param id identifier of requested comment
     * @return HTTP Status 200(OK) and Comment model or HTTP Status 404(NOT FOUND) if user/post/comment is deleted or not found
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
     * Updates comment
     * Request must be in JSON format
     * @param userId identifier of user
     * @param postId identifier of post
     * @param id identifier of comment
     * @param comment comment to be updated
     * @return HTTP Status 200(OK) if comment is successfully updated
     * and HTTP Status 404(NOT FOUND) if user/post/comment is deleted or id not found
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
     * Delete comment
     * @param userId comment author id
     * @param postId post id to which the comment was written
     * @param id identifier of a comment to be deleted
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
