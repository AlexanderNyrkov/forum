package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.CommentDAO;
import io.shuritter.spring.model.Comment;
import io.shuritter.spring.service.BaseService;
import io.shuritter.spring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("commentService")
@Transactional
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements BaseService<Comment>, CommentService {
    private CommentDAO DAO;

    @Autowired
    public void setDAO(CommentDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public void add(Comment comment) {
        this.DAO.add(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> list() {
        return this.DAO.list();
    }

    @Override
    public List<Comment> list(String postId) {
        return DAO.list(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(String id) {
        return this.DAO.getById(id);
    }

    @Override
    public void update(Comment comment) {
        this.DAO.update(comment);
    }

    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}
