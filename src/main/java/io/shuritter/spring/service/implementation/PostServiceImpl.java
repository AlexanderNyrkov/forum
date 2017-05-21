package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.PostDAO;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.BaseService;
import io.shuritter.spring.service.PostService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

@Named("postService")
@Transactional
public class PostServiceImpl extends BaseServiceImpl<Post> implements BaseService<Post>, PostService {
    private PostDAO DAO;

    public PostServiceImpl(PostDAO DAO) {
        this.DAO = DAO;
    }

    @Autowired
    @Qualifier("postDao")
    public void setDAO(PostDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public void add(Post post, User user) {
        post.setUserId(user);
        this.DAO.add(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAll(Boolean showDeleted) {
        return this.DAO.getAll(showDeleted);
    }

    @Override
    @Transactional(readOnly = true)
    public Post getById(String id) {
        return this.DAO.getById(id);
    }

    @Override
    public void update(String id, Post updated) {
        Post post = this.DAO.getById(id);
        post.setText(updated.getText());
        post.setUpdatedAt(DateTime.now());
        this.DAO.update(post);
    }

    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}
