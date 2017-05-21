package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.BaseController;
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

@RestController
@RequestMapping("/api/v1/")
public class PostControllerImpl extends BaseControllerImpl<Post> implements BaseController<Post>, PostController {
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


    private Boolean isNull(String userId, String postId) {
        return userService.getById(userId) == null || service.getById(postId) == null;
    }

    @PostMapping(value = "users/{id}/posts", consumes = "application/json")
    public ResponseEntity<Post> add(@PathVariable("id") String id, @RequestBody Post post) {
        if (userService.getById(id) == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        service.add(post, userService.getById(id));
        logger.info("Post added");
        return new ResponseEntity<>(new HttpHeaders(), CREATED);
    }

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

    @PutMapping(value = "users/{userId}/posts/{id}", consumes = "application/json")
    public ResponseEntity<Post> update(@PathVariable("userId") String userId, @PathVariable("id") String id, @RequestBody Post post) {
        if (isNull(userId, id)) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        service.update(id, post);
        logger.info("Post updated");
        return new ResponseEntity<>(new HttpHeaders(), OK);
    }

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

