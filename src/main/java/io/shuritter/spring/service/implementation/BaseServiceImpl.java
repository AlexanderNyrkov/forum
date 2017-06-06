package io.shuritter.spring.service.implementation;

import io.shuritter.spring.model.BaseEntity;
import io.shuritter.spring.service.BaseService;

/**
 * Abstract base class that implements basic service methods
 * @param <T> the type of entities
 * Implements {@link BaseService}
 * @author Alexander Nyrkov
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {
}
