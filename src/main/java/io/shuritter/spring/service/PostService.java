package io.shuritter.spring.service;

import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;

import java.util.List;

public interface PostService extends BaseService<Post> {
    void add(Post post, User user);
}