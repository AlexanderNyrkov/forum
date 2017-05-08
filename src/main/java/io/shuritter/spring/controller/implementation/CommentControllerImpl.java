package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.BaseController;
import io.shuritter.spring.controller.CommentController;
import io.shuritter.spring.model.Comment;
import io.shuritter.spring.service.CommentService;
import io.shuritter.spring.service.PostService;
import io.shuritter.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentControllerImpl extends BaseControllerImpl<Comment> implements BaseController<Comment>, CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentControllerImpl.class);
    private CommentService service;
    private UserService userService;
    private PostService postService;

    @Autowired
    public void setService(CommentService service) {
        this.service = service;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/users/{userId}/posts/{postId}/comments", consumes = "application/json")
    public ResponseEntity<Comment> add(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                       @RequestBody Comment comment) {
        userService.getById(userId);
        postService.getById(postId);
        service.add(comment);
        logger.info("Comment added");
        return new ResponseEntity<Comment>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{userId}/posts/{postId}/comments", produces = "application/json")
    public ResponseEntity<List<Comment>> list(@PathVariable("userId") String userId, @PathVariable("postId") String postId) {
        userService.getById(userId);
        postService.getById(postId);
        List<Comment> comments = service.list(postId);
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

    @GetMapping(value = "/users/posts{postId}/comments/{id}", produces = "application/json")
    public ResponseEntity<Comment> getById(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                           @PathVariable("id") String id, @RequestBody Comment comment) {
        try {
            userService.getById(userId);
            postService.getById(postId);
            service.getById(id);
            return new ResponseEntity<Comment>(comment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(value = "/users/{userId}/posts/{postId}/comments/{id}", consumes = "application/json")
    public ResponseEntity<Comment> update(@PathVariable("userId") String userId, @PathVariable("postId") String postId, @PathVariable("id") String id, @RequestBody Comment comment) {
        try {
            userService.getById(userId);
            postService.getById(postId);
            service.getById(id);
            service.update(comment);
            logger.info("Comment updated");
            return new ResponseEntity<Comment>(new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Comment>(new HttpHeaders(), HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(value = "users/{userId}/posts/{postId}/comments/{id}", consumes = "application/json")
    public ResponseEntity<Comment> delete(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                          @PathVariable("id") String id, @RequestBody Comment comment) {
        try {
            userService.getById(userId);
            postService.getById(postId);
            service.delete(id);
            logger.info("Comment with " + id + " id: " + comment);
            return new ResponseEntity<Comment>(new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Comment>(new HttpHeaders(), HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<List<Comment>> list() {
        return null;
    }

    @Override
    public ResponseEntity<Comment> add(Comment entity) {
        return null;
    }

    @Override
    public ResponseEntity<Comment> update(String id, Comment entity) {
        return null;
    }

    @Override
    public ResponseEntity<Comment> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Comment> getById(String id, Comment entity) {
        return null;
    }
}
