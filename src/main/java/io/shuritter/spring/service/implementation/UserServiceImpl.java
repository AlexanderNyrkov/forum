package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.UserDAO;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.BaseService;
import io.shuritter.spring.service.UserService;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
    public void update(User entity) {
        this.DAO.update(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return this.DAO.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(String id) {
        return this.DAO.getById(id);
    }

    @Override
    public void update(User user, String id) {
        user.setId(id);
        this.DAO.update(user);
    }

    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}
