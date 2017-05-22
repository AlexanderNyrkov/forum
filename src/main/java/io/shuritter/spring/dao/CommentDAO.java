package io.shuritter.spring.dao;

import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.Post;

import java.util.List;

/**
 * Interface whose methods are to be implemented in CommentDAOImpl class
 * Extends of {@link BaseDAO}
 * @author Alexander Nyrkov
 */
public interface CommentDAO extends BaseDAO<Comment> {
    List<Comment> getAll(Post postId, Boolean showDeleted);
}
