package io.shuritter.spring.service;

import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;

/**
 * Interface whose methods are to be implemented in PostServiceImpl class
 * Extends of {@link BaseService}
 * @author Alexander Nyrkov
 */
public interface PostService extends BaseService<Post> {
    void add(Post post, User user);
}