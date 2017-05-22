package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.CommentDAO;
import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.CommentService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Service class for CommentDAO
 * Extends of {@link BaseServiceImpl}
 * Implementation of {@link CommentService}
 * @author Alexander Nyrkov
 */
@Named("commentService")
@Transactional
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

    private CommentDAO DAO;

    public CommentServiceImpl(CommentDAO DAO) {
        this.DAO = DAO;
    }

    @Inject
    @Qualifier("commentDao")
    public void setDAO(CommentDAO DAO) {
        this.DAO = DAO;
    }

    /**
     * Added new post in database
     * @param comment The comment to be added
     * @param user Comment author
     * @param post Post to which the comment was written
     */
    @Override
    public void add(Comment comment, User user, Post post) {
        comment.setUserId(user);
        comment.setPostId(post);
        this.DAO.add(comment);
    }

    /**
     * Get all comments who are in the database
     * @param showDeleted Show all if true, and all who not deleted if false
     * @return The list comments
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll(Boolean showDeleted) {
        return this.DAO.getAll(showDeleted);
    }

    /**
     * Get all comments on the post
     * @param postId The id post comments to which should be get
     * @param showDeleted Show all if true, and all who not deleted if false
     * @return The list comment
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll(Post postId, Boolean showDeleted) {
        return DAO.getAll(postId, showDeleted);
    }

    /**
     * Get the comment with the required id
     * @param id The id for find comment
     * @return Comment with the required id
     */
    @Override
    @Transactional(readOnly = true)
    public Comment getById(String id) {
        return this.DAO.getById(id);
    }

    /**
     * Update comment text
     * @param id Comment id
     * @param updated The comment to update
     */
    @Override
    public void update(String id, Comment updated) {
        Comment comment = this.DAO.getById(id);
        comment.setText(updated.getText());
        comment.setUpdatedAt(DateTime.now());
        this.DAO.update(comment);
    }

    /**
     * Makes a logical deletion of the comment
     * @param id The id of the comment you want to delete
     */
    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}