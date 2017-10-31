package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.User;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface UserDao {

    List<User> findAll();

    User findById(Integer id);

    User save(User user);

    void delete(User user);

    void update(User user);
}
