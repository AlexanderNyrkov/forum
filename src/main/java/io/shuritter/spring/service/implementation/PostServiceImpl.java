package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.PostDAO;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.PostService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

/**
 * Service class for UserDAO
 * Extends of {@link BaseServiceImpl}
 * Implementation of {@link PostService}
 * @author Alexander Nyrkov
 */
@Named("postService")
@Transactional
public class PostServiceImpl extends BaseServiceImpl<Post> implements PostService {
    private PostDAO DAO;

    public PostServiceImpl(PostDAO DAO) {
        this.DAO = DAO;
    }

    @Autowired
    @Qualifier("postDao")
    public void setDAO(PostDAO DAO) {
        this.DAO = DAO;
    }

    /**
     * Added new post in database
     * @param post The post to be added
     * @param user Post author
     */
    @Override
    public void add(Post post, User user) {
        post.setUserId(user);
        this.DAO.add(post);
    }

    /**
     * Get all posts who are in the database
     * @param showDeleted Show all if true, and all who not deleted if false
     * @return The list posts
     */
    @Override
    @Transactional(readOnly = true)
    public List<Post> getAll(Boolean showDeleted) {
        return this.DAO.getAll(showDeleted);
    }

    /**
     * Get the post with the required id
     * @param id The id for find post
     * @return Post with the required id
     */
    @Override
    @Transactional(readOnly = true)
    public Post getById(String id) {
        return this.DAO.getById(id);
    }


    /**
     * Update post text
     * @param id Post id
     * @param updated The post to update
     */
    @Override
    public void update(String id, Post updated) {
        Post post = this.DAO.getById(id);
        post.setText(updated.getText());
        post.setUpdatedAt(DateTime.now());
        this.DAO.update(post);
    }

    /**
     * Makes a logical deletion of the post
     * @param id The id of the post you want to delete
     */
    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}
