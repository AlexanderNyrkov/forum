package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.PostDAO;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.BaseService;
import io.shuritter.spring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;

@Named("postService")
@Transactional
public class PostServiceImpl extends BaseServiceImpl<Post> implements BaseService<Post>, PostService {
    private PostDAO DAO;

    @Autowired
    @Qualifier("postDao")
    public void setDAO(PostDAO DAO) {
        this.DAO = DAO;
    }

    public PostServiceImpl(PostDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public void add(Post post, User user) {
        post.setUser(user);
        this.DAO.add(post);
    }

    @Override
    public void update(Post post, String id, String userId) {
        User user = new User();
        post.setId(id);
        user.setId(userId);
        post.setUser(user);
        this.DAO.update(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAll() {
        return this.DAO.getAll();
    }

    @Override
    public void add(Post entity) {

    }

    @Override
    @Transactional(readOnly = true)
    public Post getById(String id) {
        return this.DAO.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> userPosts(String userId) {
        return this.DAO.userPosts(userId);
    }

    @Override
    public void update(Post post) {
        this.DAO.update(post);
    }

    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}
