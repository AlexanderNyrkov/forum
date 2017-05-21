package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.CommentDAO;
import io.shuritter.spring.model.Comment;
import io.shuritter.spring.model.Post;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.BaseService;
import io.shuritter.spring.service.CommentService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("commentService")
@Transactional
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements BaseService<Comment>, CommentService {

    private CommentDAO DAO;

    public CommentServiceImpl(CommentDAO DAO) {
        this.DAO = DAO;
    }

    @Inject
    @Qualifier("commentDao")
    public void setDAO(CommentDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public void add(Comment comment, User user, Post post) {
        comment.setUserId(user);
        comment.setPostId(post);
        this.DAO.add(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll(Boolean showDeleted) {
        return this.DAO.getAll(showDeleted);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll(Post postId, Boolean showDeleted) {
        return DAO.getAll(postId, showDeleted);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(String id) {
        return this.DAO.getById(id);
    }

    @Override
    public void update(String id, Comment updated) {
        Comment comment = this.DAO.getById(id);
        comment.setText(updated.getText());
        comment.setUpdatedAt(DateTime.now());
        this.DAO.update(comment);
    }

    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}