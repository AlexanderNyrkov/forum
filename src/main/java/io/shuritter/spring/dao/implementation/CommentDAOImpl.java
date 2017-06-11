package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.CommentDAO;
import io.shuritter.spring.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * DAO class for comment entity
 * Extends {@link BaseDAOImpl}
 * Implements {@link CommentDAO}
 * @author Alexander Nyrkov
 */
@Repository("commentDao")
@Transactional
public class CommentDAOImpl extends BaseDAOImpl<Comment> implements CommentDAO {
    private static final Logger logger = LoggerFactory.getLogger(CommentDAOImpl.class);

    public CommentDAOImpl() {
    }

    /**
     * Create/Add new comment
     *
     * @param comment the comment to be add
     */
    @Override
    public void add(Comment comment) {
        entityManager.persist(comment);
        logger.info("Comment added");
    }

    /**
     * Get all comments
     *
     * @param showDeleted show all if true, and all who not deleted if false
     * @return the list comments
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll(Boolean showDeleted) {
        if (!showDeleted) {
            return entityManager.createQuery("FROM Comment c WHERE c.isDeleted = FALSE ", Comment.class).getResultList();
        }
        return entityManager.createQuery("FROM Comment", Comment.class).getResultList();
    }

    /**
     * Get comment by ID
     *
     * @param id the id for find comment
     * @return comment with the required id
     */
    @Override
    @Transactional(readOnly = true)
    public Comment getById(String id) {
        logger.info("Post successfully loaded");
        return entityManager.createQuery("FROM Comment c WHERE c.id = :id AND c.isDeleted = FALSE", Comment.class).setParameter(
                "id", id).getSingleResult();
    }

    /**
     * Update comment
     *
     * @param comment the comment to update
     */
    @Override
    public void update(Comment comment) {
        entityManager.merge(comment);
        logger.info("Comment updated");
    }

    /**
     * Logically delete comment
     *
     * @param id the id of the comment you want to delete
     */
    @Override
    public void delete(String id) {
        if (getById(id) != null) {
            entityManager.createQuery("UPDATE Comment c SET c.isDeleted = TRUE " +
                    "WHERE c.id = :id AND c.isDeleted = FALSE").setParameter("id", id).executeUpdate();
            logger.info("Comment deleted");
        }
    }
}
