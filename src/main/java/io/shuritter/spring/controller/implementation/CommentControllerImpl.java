package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.BaseController;
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
import java.util.List;

import static io.shuritter.spring.model.response.Status.ERROR;
import static io.shuritter.spring.model.response.Status.SUCCESS;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/")
public class CommentControllerImpl extends BaseControllerImpl<Comment> implements BaseController<Comment>, CommentController {
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

    private Boolean isNull(String userId, String postId, String id) {
        return userService.getById(userId) == null || postService.getById(postId) == null || service.getById(id) == null;
    }

    @PostMapping(value = "users/{userId}/posts/{postId}/comments", consumes = "application/json")
    public ResponseEntity<Comment> add(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                       @RequestBody Comment comment) {
        if (userService.getById(userId) == null || postService.getById(postId) == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        service.add(comment, userService.getById(userId), postService.getById(postId));
        logger.info("Comment added");
        return new ResponseEntity<>(new HttpHeaders(), CREATED);
    }

    @GetMapping(value = "users/{userId}/posts/{postId}/comments", produces = "application/json")
    public ResponseEntity<Response> getAll(@PathVariable("userId") String userId, @PathVariable("postId") String postId) {
        List<Comment> comments = service.getAll(postService.getById(postId), false);
        ResponseMany<Comment> response = new ResponseMany<>();
        response.setTotal(comments.size());
        response.setLimit(response.getTotal());
        response.setSkip(0);
        response.setData(comments);
        response.setStatus(SUCCESS);
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping(value = "comments", produces = "application/json")
    @Override
    public ResponseEntity<Response> getAll() {
        List<Comment> comments = service.getAll(false);
        ResponseMany<Comment> response = new ResponseMany<>();
        response.setTotal(comments.size());
        response.setLimit(response.getTotal());
        response.setSkip(0);
        response.setData(comments);
        response.setStatus(SUCCESS);
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping(value = "users/{userId}/posts/{postId}/comments/{id}", produces = "application/json")
    public ResponseEntity<Response> getById(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                            @PathVariable("id") String id) {
        ResponseOne<Comment> response = new ResponseOne<>();
        if (isNull(userId,postId,id)) {
            response.setStatus(ERROR);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.setData(service.getById(id));
        response.setStatus(SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "users/{userId}/posts/{postId}/comments/{id}", consumes = "application/json")
    public ResponseEntity<Comment> update(@PathVariable("userId") String userId, @PathVariable("postId") String postId, @PathVariable("id") String id,
                                          @RequestBody Comment comment) {
        if (isNull(userId, postId, id)) {
            return new ResponseEntity<>(new HttpHeaders(), NOT_FOUND);
        }
        service.update(id, comment);//test fff
        logger.info("Comment updated");
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }

    @DeleteMapping(value = "users/{userId}/posts/{postId}/comments/{id}", consumes = "application/json")
    public ResponseEntity<Comment> delete(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                          @PathVariable("id") String id, @RequestBody Comment comment) {
        if (isNull(userId, postId, id)) {
            return new ResponseEntity<>(new HttpHeaders(), NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }
}
