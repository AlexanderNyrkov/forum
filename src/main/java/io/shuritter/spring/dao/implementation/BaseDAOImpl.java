package io.shuritter.spring.dao.implementation;

import io.shuritter.spring.dao.BaseDAO;
import io.shuritter.spring.model.BaseEntity;

public abstract class BaseDAOImpl<T extends BaseEntity> implements BaseDAO<T> {
}
