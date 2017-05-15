package io.shuritter.spring.service;

import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;

import java.util.List;


public interface CommentService extends BaseService<Comment> {
    List<Comment> getAll(Post postId);
    void add(User user, Post post, Comment comment);
    void update(User user, Post post, Comment comment, String id);
    void delete(String id, Comment comment);
}