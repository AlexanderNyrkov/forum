package io.shuritter.spring.service.implementation;

import io.shuritter.spring.dao.UserDAO;
import io.shuritter.spring.model.User;
import io.shuritter.spring.service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Service class for UserDAO
 * Extends of {@link BaseServiceImpl}
 * Implementation of {@link UserService}
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
     * Added new user in database
     * @param user The user to be added
     */
    @Override
    public void add(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        this.DAO.add(user);
    }

    /**
     * Get all users who are in the database
     * @param showDeleted Show all if true, and all who not deleted if false
     * @return The list users
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getAll(Boolean showDeleted) {
        return this.DAO.getAll(showDeleted);
    }

    /**
     * Get the user with the required id
     * @param id The id for find user
     * @return User with the required id
     */
    @Override
    @Transactional(readOnly = true)
    public User getById(String id) {
        return this.DAO.getById(id);
    }

    /**
     * Update user login, name, password, email
     * @param id The user id
     * @param updated The user to update
     */
    @Override
    public void update(String id, User updated) {
        User user = this.DAO.getById(id);
        user.setLogin(updated.getLogin());
        user.setName(updated.getName());
        user.setPassword(BCrypt.hashpw(updated.getPassword(), BCrypt.gensalt()));
        user.setEmail(updated.getEmail());
        user.setUpdatedAt(DateTime.now());
        this.DAO.update(user);
    }

    /**
     * Makes a logical deletion of the user
     * @param id The id of the user you want to delete
     */
    @Override
    public void delete(String id) {
        this.DAO.delete(id);
    }
}
