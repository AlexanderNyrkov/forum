package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
import io.shuritter.spring.model.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Abstract base class that implements basic DAO methods
 * @param <T> the type of entities
 * Implements {@link BaseDAO}
 * @author Alexander Nyrkov
 */
abstract class BaseDAOImpl<T extends BaseEntity> implements BaseDAO<T> {
    @PersistenceContext
    protected EntityManager entityManager;
}
