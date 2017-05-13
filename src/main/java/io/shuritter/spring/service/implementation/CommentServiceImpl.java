package io.shuritter.spring.service.implementation;//package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.CommentDAO;
import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.BaseService;
import io.shuritter.spring.service.CommentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("commentService")
@Transactional
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements BaseService<Comment>, CommentService {

    private CommentDAO DAO;

    @Inject
    @Qualifier("commentDao")
    public void setDAO(CommentDAO DAO) {
        this.DAO = DAO;
    }

    public CommentServiceImpl(CommentDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public void add(Comment comment) {
        this.DAO.add(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll() {
        return this.DAO.getAll();
    }

    @Override
    public List<Comment> getAll(String postId) {
        return DAO.getAll(postId);
    }

    @Override
    public void add(User user, Post post, Comment comment) {
        comment.setUser(user);
        comment.setPost(post);
        this.DAO.add(comment);
    }

    @Override
    public void update(Comment comment) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(String id) {
        return this.DAO.getById(id);
    }

    @Override
    public void update(User user, Post post, Comment comment, String id) {
        comment.setId(id);
        comment.setUser(user);
        comment.setPost(post);
        this.DAO.update(comment);
    }

    @Override
    public void delete(String id, Comment comment) {
        this.DAO.delete(id);
    }
}