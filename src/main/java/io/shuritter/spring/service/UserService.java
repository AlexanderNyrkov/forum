package io.shuritter.spring.service;

import io.shuritter.spring.model.User;

/**
 * Interface whose methods are to be implemented in UserServiceImpl class
 * Extends of {@link BaseService}
 * @author Alexander Nyrkov
 */
public interface UserService extends BaseService<User>{
    void add(User user);

}