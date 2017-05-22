package io.shuritter.spring.service.implementation;

import io.shuritter.spring.model.BaseEntity;
import io.shuritter.spring.service.BaseService;

/**
 * Abstract base class for Service classes
 * @param <T> Describes any entity class
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {
}
