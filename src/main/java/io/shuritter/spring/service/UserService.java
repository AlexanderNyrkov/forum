package io.shuritter.spring.service;

import io.shuritter.spring.model.User;

/**
 * Interface that contains methods to control UserServiceImpl class
 * Extends {@link BaseService}
 * @author Alexander Nyrkov
 */
public interface UserService extends BaseService<User>{
    void add(User user);

}