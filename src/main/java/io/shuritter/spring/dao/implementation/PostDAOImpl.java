package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.PostDAO;
import io.shuritter.spring.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    public PostDAOImpl() {
    }

    /**
     * Create/Add new post
     *
     * @param post the post to be add
     */
    @Override
    public void add(Post post) {
        entityManager.persist(post);
        logger.info("Post added");
    }

    /**
     * Get all posts
     *
     * @param showDeleted show all if true, and all who not deleted if false
     * @return the list posts
     */
    @Override
    @Transactional(readOnly = true)
    public List<Post> getAll(Boolean showDeleted) {
        if (!showDeleted) {
            return entityManager.createQuery("FROM Post p WHERE p.isDeleted = FALSE ", Post.class).getResultList();
        }
        return entityManager.createQuery("FROM Post", Post.class).getResultList();
    }

    /**
     * Get post by ID
     *
     * @param id the id for find post
     * @return post with the required id
     */
    @Override
    @Transactional(readOnly = true)
    public Post getById(String id) {
        logger.info("Post successfully loaded");
        return entityManager.createQuery("FROM Post p WHERE p.id = :id AND p.isDeleted = FALSE", Post.class).setParameter(
                "id", id).getSingleResult();
    }

    /**
     * Update post
     *
     * @param post the post to update
     */
    @Override
    public void update(Post post) {
        entityManager.merge(post);
        logger.info("Post updated");
    }

    /**
     * Logically delete post
     *
     * @param id the id of the post you want to delete
     */
    @Override
    public void delete(String id) {
        entityManager.createQuery("UPDATE Post p SET p.isDeleted = TRUE " +
                "WHERE p.id = :id AND p.isDeleted = FALSE").setParameter("id", id).executeUpdate();
        logger.info("Post successfully removed");

    }
}
