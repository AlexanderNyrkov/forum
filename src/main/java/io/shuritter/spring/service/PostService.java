package io.shuritter.spring.service;

import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;

/**
 * Interface that contains methods to control PostServiceImpl class
 * Extends {@link BaseService}
 * @author Alexander Nyrkov
 */
public interface PostService extends BaseService<Post> {
    void add(Post post, User user);
}