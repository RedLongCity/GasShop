package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.User;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface UserDao extends AbstractDao<User> {

    @Override
    List<User> findAll();

    @Override
    User findById(Integer id);

    @Override
    User save(User user);

    @Override
    void delete(User user);

    @Override
    void update(User user);
}
