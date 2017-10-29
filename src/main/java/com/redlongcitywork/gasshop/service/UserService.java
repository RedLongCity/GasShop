package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.models.User;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface UserService {

    List<User> findAll();

    User findById(Integer id);

    void saveUser(User user);

    void deleteUser(User user);

    void updateUser(User user);

    void deleteAll();
}
