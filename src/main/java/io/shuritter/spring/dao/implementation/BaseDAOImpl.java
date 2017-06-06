package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
import io.shuritter.spring.model.BaseEntity;
import org.hibernate.SessionFactory;

/**
 * Abstract base class that implements basic DAO methods
 * @param <T> the type of entities
 * Implements {@link BaseDAO}
 * @author Alexander Nyrkov
 */
abstract class BaseDAOImpl<T extends BaseEntity> implements BaseDAO<T> {
    protected SessionFactory sessionFactory;
}
