package io.shuritter.spring.dao.implementation;

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

/**
 * DAO class for post entity
 * Extends {@link BaseDAOImpl}
 * Implements {@link PostDAO}
 * @author Alexander Nyrkov
 */
@Repository("postDao")
@Transactional
public class PostDAOImpl extends BaseDAOImpl<Post> implements PostDAO {
    private static final Logger logger = LoggerFactory.getLogger(PostDAOImpl.class);

    @Inject
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PostDAOImpl() {
    }

    public PostDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Create/Add new post
     * @param post the post to be add
     */
    @Override
    public void add(Post post) {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(post);
        logger.info("Post added");
    }

    /**
     * Get all posts
     * @param showDeleted show all if true, and all who not deleted if false
     * @return the list posts
     */
    @Override
    @Transactional(readOnly = true)
    public List<Post> getAll(Boolean showDeleted) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Post> posts;
        if (!showDeleted) {
            posts = session.createQuery("FROM Post p WHERE p.isDeleted = FALSE", Post.class).list();
        } else {
            posts = session.createQuery("FROM Post", Post.class).list();
        }
        return posts;
    }

    /**
     * Get post by ID
     * @param id the id for find post
     * @return post with the required id
     */
    @Override
    @Transactional(readOnly = true)
    public Post getById(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        logger.info("Post successfully loaded");
        return session.createQuery("FROM Post p WHERE p.id = :id AND p.isDeleted = FALSE", Post.class)
                .setParameter("id", id).uniqueResult();
    }

    /**
     * Update post
     * @param post the post to update
     */
    @Override
    public void update(Post post) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(post);
        logger.info("Post updated");
    }

    /**
     * Logically delete post
     * @param id the id of the post you want to delete
     */
    @Override
    public void delete(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.createQuery("UPDATE Post p SET p.isDeleted = TRUE " +
                "WHERE p.id = :id AND p.isDeleted = FALSE").setParameter("id", id).executeUpdate();
        logger.info("Post successfully removed");

    }
}
