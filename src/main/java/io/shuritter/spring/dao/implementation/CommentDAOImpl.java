package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
import io.shuritter.spring.dao.CommentDAO;
import io.shuritter.spring.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository("commentDao")
public class CommentDAOImpl extends BaseDAOImpl<Comment> implements BaseDAO<Comment>, CommentDAO {
    private static final Logger logger = LoggerFactory.getLogger(CommentDAOImpl.class);

    private SessionFactory sessionFactory;

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
        logger.info("Comment add: " + comment);
    }

    @Override
    public List<Comment> getAll(String postId) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("FROM Comment c WHERE c.post = :postId AND c.isDeleted = FALSE")
                .setParameter("postId", postId).list();
    }

    @Override
    public List<Comment> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Comment> comments = session.createQuery("FROM Comment c WHERE c.isDeleted = FALSE").list();
        logger.info("User getAll: " + comments.toString());
        return comments;
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
            session.createQuery("UPDATE Comment c SET c.isDeleted = TRUE WHERE c.id = :id AND c.isDeleted = FALSE ").
                    setParameter("id", id).executeUpdate();
            logger.info("Comment deleted");
        }
    }

    @Override
    public Comment getById(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Comment) session.createQuery("FROM Comment c WHERE c.id = :id AND c.isDeleted = FALSE ").setParameter("id", id).uniqueResult();
    }
}
