package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.BaseController;
import io.shuritter.spring.controller.PostController;
import io.shuritter.spring.model.Post;
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
public class PostControllerImpl extends BaseControllerImpl<Post> implements BaseController<Post>, PostController{
    private static final Logger logger = LoggerFactory.getLogger(PostControllerImpl.class);


    private PostService service;

    private UserService userService;

    @Autowired
    public void setService(PostService service) {
        this.service = service;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users/{id}/posts",  consumes = "application/json")
    public ResponseEntity<Post> add(@PathVariable("id") String id, @RequestBody Post post) {
        userService.getById(id);
        service.add(post);
        logger.info("Post added");
        return new ResponseEntity<Post>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/posts", produces = "application/json")
    public ResponseEntity<List<Post>> list() {
        List<Post> posts = this.service.list();
        if (posts.size() == 0) {
            logger.info("No posts");
            return new ResponseEntity<List<Post>>(HttpStatus.NO_CONTENT);
        }
        logger.info(this.service.list().toString());
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}/posts",  produces = "application/json")
    public ResponseEntity<List<Post>> userPosts(@PathVariable("id") String id) {
        List<Post> posts = this.service.userPosts(id);

        if (posts.size() == 0) {
            logger.info("No comments");
            return new ResponseEntity<List<Post>>(HttpStatus.NO_CONTENT);
        }

        logger.info(this.service.userPosts(id).toString());
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}/posts/{id}", produces = "application/json")
    public ResponseEntity<Post> getById(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody Post post) {
        try {
            userService.getById(userId);
            service.getById(id);
            logger.info("Post with " + id + " id: " + post);
            return new ResponseEntity<Post>(post, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Post with " + id + " id not found");
            return new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(value = "/users/{userId}/posts/{id}", consumes = "application/json")
    public ResponseEntity<Post> update(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody Post post) {
        userService.getById(userId);
        service.getById(id);
        service.update(post);
        logger.info("Post updated");
        return new ResponseEntity<Post>(new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{userId}/posts/{id}", consumes = "application/json")
    public ResponseEntity<Post> delete(@PathVariable("userId") String userId, @PathVariable("id") @RequestBody String id) {
        userService.getById(userId);
        service.delete(id);
        logger.info("Post with");
        return new ResponseEntity<Post>(new HttpHeaders(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Post> add(Post entity) {
        return null;
    }

    @Override
    public ResponseEntity<Post> update(String id, Post entity) {
        return null;
    }

    @Override
    public ResponseEntity<Post> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Post> getById(String id, Post entity) {
        return null;
    }
}

