package io.shuritter.spring.service;

import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;

import java.util.List;


public interface CommentService extends BaseService<Comment> {
    List<Comment> getAll(Post postId, Boolean showDeleted);
    void add(Comment comment, User user, Post post);
}