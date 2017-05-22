package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.BaseController;
import io.shuritter.spring.model.BaseEntity;

/**
 * Abstract base class for Controller classes
 * @param <T> Describes any entity class
 * Implementation of {@link BaseController}
 * @author Alexander Nyrkov
 */
public abstract class BaseControllerImpl<T extends BaseEntity> implements BaseController<T> {

}
