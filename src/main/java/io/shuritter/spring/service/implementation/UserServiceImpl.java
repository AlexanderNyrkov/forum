package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.UserDAO;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.BaseService;
import io.shuritter.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements BaseService<User>, UserService {
    private UserDAO DAO;

    @Autowired
    public void setDAO(UserDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public void add(User user) {
        this.DAO.add(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> list() {
        return this.DAO.list();
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(String id) {
        return this.DAO.getById(id);
    }

    @Override
    public void update(User user) {
        this.DAO.update(user);
    }

    @Override
    @Transactional
    public void delete(String user) {
        this.DAO.delete(user);
    }
}
