package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
import io.shuritter.spring.dao.UserDAO;
import io.shuritter.spring.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userDao")
public class UserDAOImpl extends BaseDAOImpl<User> implements BaseDAO<User>, UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public UserDAOImpl() {
    }

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> list() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> users = session.createQuery("FROM User u WHERE u.is_deleted = FALSE ").list();
        logger.info("Book list: " + users.toString());

        return users;
    }

    @Override
    @Transactional
    public void add(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(user);
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
        User user = session.load(User.class, new String(id));

        if(user!=null){
            Query query = session.createQuery("UPDATE User u SET u.is_deleted = TRUE WHERE u.id =: id");
            query.setParameter("id", id);
            logger.info("User successfully removed");
        } else {
            logger.info("User not found");
        }
    }

    @Override
    @Transactional
    public User getById(String id) {
        Session session =this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new String(id));
        logger.info("Book successfully loaded. Book details: ");

        return user;
    }
}