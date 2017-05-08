package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
import io.shuritter.spring.dao.CommentDAO;
import io.shuritter.spring.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commentDao")
public class CommentDAOImpl extends BaseDAOImpl<Comment> implements BaseDAO<Comment>, CommentDAO {
    private static final Logger logger = LoggerFactory.getLogger(CommentDAOImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
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
        session.save(comment);
        logger.info("Comment add: " + comment);
    }

    @Override
    public List<Comment> list(String postId) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Comment c WHERE c.post=: postId AND " +
                "c.is_deleted = FALSE");
        query.setParameter("postId", postId);
        List<Comment> comments = query.list();
        logger.info("User list: " + comments.toString());
        return comments;
    }

    @Override
    public List<Comment> list() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Comment> comments = session.createQuery("FROM Comment").list();
        logger.info("User list: " + comments.toString());
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
        Comment comment = (Comment) session.load(Comment.class, new String(id));

        if(comment!=null){
            Query delete = session.createQuery("UPDATE Comment c SET c.is_deleted = true, c.updated_at = current_date " +
                    "WHERE c.id=: id");
            delete.setParameter("id", id);
            delete.executeUpdate();
            logger.info("Comment deleted");
        }
    }

    @Override
    public Comment getById(String id) {
        Session session =this.sessionFactory.getCurrentSession();
        Comment comment = (Comment) session.load(Comment.class, new String(id));

        return comment;
    }
}
