package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.UserProfile;
import java.util.List;

/**
 *
 * @author redlongcity 28/10/2017
 */
public interface UserProfileDao extends AbstractDao<UserProfile> {

    @Override
    List<UserProfile> findAll();

    @Override
    UserProfile findById(Integer id);

    @Override
    UserProfile save(UserProfile profile);

    @Override
    void delete(UserProfile profile);

    @Override
    void update(UserProfile profile);

}
