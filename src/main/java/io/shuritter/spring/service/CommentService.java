package io.shuritter.spring.service;

import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;

import java.util.List;

/**
 * Interface that contains methods to control CommentServiceImpl class
 * Extends {@link BaseService}
 * @author Alexander Nyrkov
 */
public interface CommentService extends BaseService<Comment> {
    List<Comment> getAll(Post postId, Boolean showDeleted);
    void add(Comment comment, User user, Post post);
}