package io.shuritter.spring.service;

import io.shuritter.spring.model.Post;

import java.util.List;

public interface PostService extends BaseService<Post> {
    List<Post> userPosts(String userId);
}