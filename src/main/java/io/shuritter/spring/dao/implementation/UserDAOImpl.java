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
public class UserDAOImpl extends BaseDAOImpl<User> implements BaseDAO<User>, UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    private SessionFactory sessionFactory;

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
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> users = session.createQuery("FROM User u WHERE u.isDeleted = FALSE").list();
        logger.info("User getAll: " + users.toString());
        return users;
    }

    @Override
    @Transactional
    public void add(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
        logger.info("User added");
    }

    @Override
    @Transactional
    public void update(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
        logger.info("User updated");
    }

    @Override
    @Transactional
    public void delete(String id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.createQuery("UPDATE User u SET u.isDeleted = TRUE WHERE u.id = :id")
                .setParameter("id", id).executeUpdate();
        logger.info("User successfully removed");
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(String id) {
        Session session =this.sessionFactory.getCurrentSession();
        return (User)session.createQuery("FROM User u WHERE u.id = :id AND u.isDeleted = FALSE ")
                .setParameter("id", id).uniqueResult();
    }
}