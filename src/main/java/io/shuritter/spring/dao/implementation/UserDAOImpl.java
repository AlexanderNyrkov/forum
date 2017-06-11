package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.UserDAO;
import io.shuritter.spring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * DAO class for User entity
 * Extends {@link BaseDAOImpl}
 * Implements of {@link UserDAO}
 * @author Alexander Nyrkov
 */
@Repository("userDAO")
@Transactional
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    public UserDAOImpl() {
    }

    /**
     * Create/Add new user
     *
     * @param user the user to be add
     */
    @Override
    public void add(User user) {
        entityManager.persist(user);
        logger.info("User added");
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
        if (!showDeleted) {
            return entityManager.createQuery("FROM User u WHERE u.isDeleted = FALSE ", User.class).getResultList();
        }
        return entityManager.createQuery("FROM User", User.class).getResultList();
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
        logger.info("User successfully loaded");
        return entityManager.createQuery("FROM User u WHERE u.id = :id AND u.isDeleted = FALSE", User.class).setParameter(
                "id", id).getSingleResult();
    }

    /**
     * Update user
     *
     * @param user the user to update
     */
    @Override
    public void update(User user) {
        entityManager.merge(user);
        logger.info("User updated");
    }

    /**
     * Logically delete user
     *
     * @param id the id of the user you want to delete
     */
    @Override
    public void delete(String id) {
        entityManager.createQuery("UPDATE User u SET u.isDeleted = TRUE " +
                "WHERE u.id = :id AND u.isDeleted = FALSE").setParameter("id", id).executeUpdate();
        logger.info("User successfully removed");
    }
}