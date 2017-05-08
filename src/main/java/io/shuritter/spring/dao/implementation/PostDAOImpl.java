package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
import io.shuritter.spring.dao.PostDAO;
import io.shuritter.spring.model.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import java.util.List;

@Repository("postDao")
public class PostDAOImpl extends BaseDAOImpl<Post> implements BaseDAO<Post>, PostDAO {
    private static final Logger logger = LoggerFactory.getLogger(PostDAOImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public PostDAOImpl() {
    }

    public PostDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Post post) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(post);
        logger.info("Post added");
    }

    @Override
    public Post getById(String id) {
        Session session =this.sessionFactory.getCurrentSession();
        Post post = (Post) session.load(Post.class, new String(id));
        logger.info("Post successfully loaded");

        return post;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Post> list() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Post> posts = session.createQuery("FROM Post p WHERE p.is_deleted = FALSE ").list();
        for (Post post : posts) {
            logger.info("Post list: " + post);
        }
        return posts;
    }

    @Override
    public List<Post> userPosts(String userId) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Post WHERE user=:id AND p.user = FALSE ");
        query.setParameter("id", userId);
        return query.list();
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
        Post post = (Post) session.load(Post.class, new String(id));

        if(post!=null){
            Query delete = session.createQuery("UPDATE Post p SET ip.s_deleted = TRUE , p.updated_at = current_date " +
                    "WHERE p.id=: id");
            delete.setParameter("id", id);
            delete.executeUpdate();
            logger.info("Post successfully removed");
        } else {
            logger.info("Post not found");
        }

    }
}
