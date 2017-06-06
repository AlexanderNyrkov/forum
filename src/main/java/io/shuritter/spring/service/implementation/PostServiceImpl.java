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
 * Service class for post DAO
 * Extends {@link BaseServiceImpl}
 * Implements {@link PostService}
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
     * Create/Add new post
     * @param post the post to be add
     * @param user post author
     */
    @Override
    public void add(Post post, User user) {
        post.setUserId(user);
        this.DAO.add(post);
    }

    /**
     * Get all posts
     * @param showDeleted show all if true, and all who not deleted if false
     * @return the list posts
     */
    @Override
    @Transactional(readOnly = true)
    public List<Post> getAll(Boolean showDeleted) {
        return this.DAO.getAll(showDeleted);
    }

    /**
     * Get post by ID
     * @param id identifier of post
     * @return post with the required id
     */
    @Override
    @Transactional(readOnly = true)
    public Post getById(String id) {
        return this.DAO.getById(id);
    }


    /**
     * Update post
     * @param id identifier of post
     * @param updated the post to update
     */
    @Override
    public void update(String id, Post updated) {
        Post post = this.DAO.getById(id);
        post.setText(updated.getText());
        post.setUpdatedAt(DateTime.now());
        this.DAO.update(post);
    }

    /**
     * Logically delete post
     * @param id the id of the post you want to delete
     */
    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}
