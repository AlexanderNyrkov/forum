package io.shuritter.spring.service;

import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;

import java.util.List;

public interface PostService extends BaseService<Post> {
    List userPosts(String userId);
    void add(Post post, User user);
    void update(Post post, String id, String userId);
}