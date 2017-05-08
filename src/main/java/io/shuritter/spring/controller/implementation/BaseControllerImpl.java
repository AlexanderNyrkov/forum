package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.BaseController;
import io.shuritter.spring.model.BaseEntity;

public abstract class BaseControllerImpl<T extends BaseEntity> implements BaseController<T> {
}
