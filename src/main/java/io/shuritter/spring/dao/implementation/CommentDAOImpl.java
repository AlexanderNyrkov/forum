package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
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

@Repository("commentDao")
@Transactional
public class CommentDAOImpl extends BaseDAOImpl<Comment> implements BaseDAO<Comment>, CommentDAO {
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

    @Override
    public void add(Comment comment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(comment);
        logger.info("Comment added");
    }

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

    @Override
    @Transactional(readOnly = true)
    public Comment getById(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("FROM Comment c WHERE c.id = :id AND c.isDeleted = FALSE ", Comment.class)
                .setParameter("id", id).uniqueResult();
    }

    @Override
    public void update(Comment comment) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(comment);
    }

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
