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
 * Service class for comment DAO
 * Extends {@link BaseServiceImpl}
 * Implements {@link CommentService}
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
     * Create/Add new comment
     * @param comment the comment to be add
     * @param user comment author
     * @param post post to which the comment was written
     */
    @Override
    public void add(Comment comment, User user, Post post) {
        comment.setUserId(user);
        comment.setPostId(post);
        this.DAO.add(comment);
    }

    /**
     * Get all comments
     * @param showDeleted show all if true, and all who not deleted if false
     * @return the list comments
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll(Boolean showDeleted) {
        return this.DAO.getAll(showDeleted);
    }

    /**
     * Get all post comments
     * @param postId the id post comments to which should be get
     * @param showDeleted show all if true, and all who not deleted if false
     * @return the list comment
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll(Post postId, Boolean showDeleted) {
        return DAO.getAll(postId, showDeleted);
    }

    /**
     * Get comment by ID
     * @param id the id for find comment
     * @return comment with the required id
     */
    @Override
    @Transactional(readOnly = true)
    public Comment getById(String id) {
        return this.DAO.getById(id);
    }

    /**
     * Update comment
     * @param id identifier of comment
     * @param updated the comment to update
     */
    @Override
    public void update(String id, Comment updated) {
        Comment comment = this.DAO.getById(id);
        comment.setText(updated.getText());
        comment.setUpdatedAt(DateTime.now());
        this.DAO.update(comment);
    }

    /**
     * Logically delete comment
     * @param id the id of the comment you want to delete
     */
    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}