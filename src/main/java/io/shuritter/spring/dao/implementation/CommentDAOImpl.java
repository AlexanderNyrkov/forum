package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.CommentDAO;
import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * DAO class for comment entity
 * Extends of {@link BaseDAOImpl}
 * Implementation of {@link CommentDAO}
 * @author Alexander Nyrkov
 */
@Repository("commentDao")
@Transactional
public class CommentDAOImpl extends BaseDAOImpl<Comment> implements CommentDAO {
    private static final Logger logger = LoggerFactory.getLogger(CommentDAOImpl.class);

    @Inject
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CommentDAOImpl() {
    }

    public CommentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Added new comment in database
     * @param comment The comment to be added
     */
    @Override
    public void add(Comment comment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(comment);
        logger.info("Comment added");
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
        Session session = this.sessionFactory.getCurrentSession();
        List<Comment> comments;
        if (!showDeleted) {
            comments = session.createQuery("FROM Comment c WHERE c.postId = :postId AND c.isDeleted = FALSE", Comment.class)
                    .setParameter("postId", postId).list();
        } else {
            comments = session.createQuery("FROM Comment c WHERE c.postId = :postId", Comment.class)
                    .setParameter("postId", postId).list();
        }
        return comments;
    }

    /**
     * Get all comments who are in the database
     * @param showDeleted Show all if true, and all who not deleted if false
     * @return The list comments
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll(Boolean showDeleted) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Comment> comments;
        if (!showDeleted) {
            comments = session.createQuery("FROM Comment c WHERE c.isDeleted = FALSE", Comment.class).list();
        } else {
            comments = session.createQuery("FROM Comment", Comment.class).list();
        }
        logger.info("User getAll: " + comments.toString());
        return comments;
    }

    /**
     * Get the comment with the required id
     * @param id The id for find comment
     * @return Comment with the required id
     */
    @Override
    @Transactional(readOnly = true)
    public Comment getById(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("FROM Comment c WHERE c.id = :id AND c.isDeleted = FALSE ", Comment.class)
                .setParameter("id", id).uniqueResult();
    }

    /**
     * Update comment text
     * @param comment The comment to update
     */
    @Override
    public void update(Comment comment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(comment);
    }

    /**
     * Makes a logical deletion of the comment
     * @param id The id of the comment you want to delete
     */
    @Override
    public void delete(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        if (getById(id) != null) {
            session.createQuery("UPDATE Comment c SET c.isDeleted = TRUE WHERE c.id = :id " +
                    "AND c.isDeleted = FALSE ").
                    setParameter("id", id).executeUpdate();
            logger.info("Comment deleted");
        }
    }
}
