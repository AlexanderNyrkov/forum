package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.PostDAO;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.service.BaseService;
import io.shuritter.spring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("postService")
@Transactional
public class PostServiceImpl extends BaseServiceImpl<Post> implements BaseService<Post>, PostService {
    private PostDAO DAO;

    @Autowired
    public void setDAO(PostDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public void add(Post post) {
        this.DAO.add(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> list() {
        return this.DAO.list();
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
