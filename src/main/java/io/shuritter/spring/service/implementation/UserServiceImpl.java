package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.UserDAO;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.BaseService;
import io.shuritter.spring.service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements BaseService<User>, UserService {
    private UserDAO DAO;

    @Inject
    @Qualifier("userDAO")
    public void setDAO(UserDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public void add(User user) {
        this.DAO.add(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll(Boolean showDeleted) {
        return this.DAO.getAll(showDeleted);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(String id) {
        return this.DAO.getById(id);
    }

    @Override
    public void update(String id, User updated) {
        User user = this.DAO.getById(id);
        user.setLogin(updated.getLogin());
        user.setName(updated.getName());
        user.setPassword(updated.getPassword());
        user.setEmail(updated.getEmail());
        user.setUpdatedAt(DateTime.now());
        this.DAO.update(user);
    }

    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}
