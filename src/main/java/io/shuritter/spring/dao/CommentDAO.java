package io.shuritter.spring.dao;

import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.Post;

import java.util.List;


public interface CommentDAO extends BaseDAO<Comment> {
    List<Comment> getAll(Post postId);
}
