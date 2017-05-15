package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.BaseController;
import io.shuritter.spring.controller.CommentController;
import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;
import io.shuritter.spring.model.response.Response;
import io.shuritter.spring.service.CommentService;
import io.shuritter.spring.service.PostService;
import io.shuritter.spring.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
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

    @PostMapping(value = "/users/{userId}/posts/{postId}/comments", consumes = "application/json")
    public ResponseEntity<Comment> add(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                       @RequestBody Comment comment) {
        User user = userService.getById(userId);
        Post post = postService.getById(postId);
        if (user == null || post == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        service.add(user, post, comment);
        logger.info("Comment added");
        return new ResponseEntity<>(new HttpHeaders(), CREATED);
    }

    @GetMapping(value = "/users/{userId}/posts/{postId}/comments", produces = "application/json")
    public ResponseEntity<List<Comment>> getAll(@PathVariable("userId") String userId, @PathVariable("postId") String postId) {
        if (userService.getById(userId) == null || postService.getById(postId) == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        List<Comment> comments = service.getAll(postService.getById(postId));
        return new ResponseEntity<>(comments, OK);
    }

    @GetMapping(value = "/users/{userId}/posts/{postId}/comments/{id}", produces = "application/json")
    public ResponseEntity<Comment> getById(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                           @PathVariable("id") String id) {
        if (empty(userId, postId, id) || deleted(userId, postId, id)) {
            return new ResponseEntity<>(new HttpHeaders(), NOT_FOUND);
        }
        return new ResponseEntity<>(service.getById(id), OK);

    }

    @PutMapping(value = "/users/{userId}/posts/{postId}/comments/{id}", consumes = "application/json")
    public ResponseEntity<Comment> update(@PathVariable("userId") String userId, @PathVariable("postId") String postId, @PathVariable("id") String id,
                                          @RequestBody Comment comment) {
        if (empty(userId, postId, id) || deleted(userId, postId, id)) {
            return new ResponseEntity<>(new HttpHeaders(), NOT_FOUND);
        }
        service.update(userService.getById(userId), postService.getById(postId), comment, id);//test fff
        logger.info("Comment updated");
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }

    @DeleteMapping(value = "users/{userId}/posts/{postId}/comments/{id}", consumes = "application/json")
    public ResponseEntity<Comment> delete(@PathVariable("userId") String userId, @PathVariable("postId") String postId,
                                          @PathVariable("id") String id, @RequestBody Comment comment) {

        if (empty(userId, postId, id) || deleted(userId, postId, id)) {
            return new ResponseEntity<>(new HttpHeaders(), NOT_FOUND);
        }
        service.delete(id, comment);
        logger.info("Comment with " + id + " id: " + comment);
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }

    public Boolean empty(String userId, String postId, String id) {
        return userService.getById(userId) == null || postService.getById(postId) == null || service.getById(id) == null;
    }



    public Boolean deleted(String userId, String postId, String id) {
        return userService.getById(userId).isDeleted() || postService.getById(postId).isDeleted() || service.getById(id).isDeleted();
    }





    @Override
    public ResponseEntity<Response> getAll() {
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
    public ResponseEntity<Response> getById(String id) {
        return null;
    }

}
