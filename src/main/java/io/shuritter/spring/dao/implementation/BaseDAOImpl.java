package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
import io.shuritter.spring.model.BaseEntity;
import org.hibernate.SessionFactory;

/**
 * Abstract base class for DAO classes
 * @param <T> Describes any entity class
 * Implementation of {@link BaseDAO}
 * @author Alexander Nyrkov
 */
abstract class BaseDAOImpl<T extends BaseEntity> implements BaseDAO<T> {
    protected SessionFactory sessionFactory;
}
