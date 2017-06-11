package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.UserDAO;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

/**
 * Service class for user DAO
 * Extends {@link BaseServiceImpl}
 * Implements {@link UserService}
 * @author Alexander Nyrkov
 */
@Named("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    private UserDAO DAO;

    @Inject
    @Qualifier("userDAO")
    public void setDAO(UserDAO DAO) {
        this.DAO = DAO;
    }

    /**
     * Create/Add new user
     *
     * @param user the user to be add
     */
    @Override
    public void add(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        this.DAO.add(user);
    }

    /**
     * Get all users
     *
     * @param showDeleted show all if true, and all who not deleted if false
     * @return the list users
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getAll(Boolean showDeleted) {
        return this.DAO.getAll(showDeleted);
    }

    /**
     * Get user by ID
     *
     * @param id the id for find user
     * @return user with the required id
     */
    @Override
    @Transactional(readOnly = true)
    public User getById(String id) {
        return this.DAO.getById(id);
    }

    /**
     * Update user
     *
     * @param id      identifier of user
     * @param updated the user to update
     */
    @Override
    public void update(String id, User updated) {
        User user = this.DAO.getById(id);
        user.setLogin(updated.getLogin());
        user.setName(updated.getName());
        user.setPassword(BCrypt.hashpw(updated.getPassword(), BCrypt.gensalt()));
        user.setEmail(updated.getEmail());
        user.setUpdatedAt(new Date());
        this.DAO.update(user);
    }

    /**
     * Logically delete user
     *
     * @param id the id of the user you want to delete
     */
    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}
