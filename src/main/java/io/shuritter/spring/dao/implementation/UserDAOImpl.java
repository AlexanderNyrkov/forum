package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
import io.shuritter.spring.dao.UserDAO;
import io.shuritter.spring.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("userDAO")
@Transactional
public class UserDAOImpl extends BaseDAOImpl<User> implements BaseDAO<User>, UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Inject
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserDAOImpl() {
    }

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
        logger.info("User added");
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll(Boolean showDeleted) {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> users;
        if (!showDeleted) {
            users = session.createQuery("FROM User u WHERE u.isDeleted = FALSE", User.class).list();
        } else {
            users = session.createQuery("FROM User", User.class).list();
        }

        logger.info("User getAll: " + users.toString());
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("FROM User u WHERE u.id = :id AND u.isDeleted = FALSE", User.class)
                .setParameter("id", id).uniqueResult();
    }

    @Override
    public void update(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
        logger.info("User updated");
    }

    @Override
    public void delete(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.createQuery("UPDATE User u SET u.isDeleted = TRUE WHERE u.id = :id " +
                "AND u.isDeleted = FALSE")
                .setParameter("id", id).executeUpdate();
        logger.info("User successfully removed");
    }
}