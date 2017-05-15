package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
import io.shuritter.spring.dao.PostDAO;
import io.shuritter.spring.model.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("postDao")
public class PostDAOImpl extends BaseDAOImpl<Post> implements BaseDAO<Post>, PostDAO {
    private static final Logger logger = LoggerFactory.getLogger(PostDAOImpl.class);

    private SessionFactory sessionFactory;

    @Inject
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PostDAOImpl() {
    }

    public PostDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Post post) {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(post);
        logger.info("Post added");
    }

    @Override
    public Post getById(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        logger.info("Post successfully loaded");
        return (Post) session.createQuery("FROM Post p WHERE p.id = :id")
                .setParameter("id", id).uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Post> posts = session.createQuery("FROM Post p WHERE p.isDeleted = FALSE ").list();
        return posts;
    }

    @Override
    public List<Post> userPosts(String userId) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("FROM Post p WHERE p.user.id = :id").setParameter("id", userId).list();
    }

    @Override
    public void update(Post post) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(post);
        logger.info("Post updated");
    }

    @Override
    public void delete(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.createQuery("UPDATE Post p SET p.isDeleted = TRUE " +
                "WHERE p.id = :id").setParameter("id", id).executeUpdate();
        logger.info("Post successfully removed");

    }
}
