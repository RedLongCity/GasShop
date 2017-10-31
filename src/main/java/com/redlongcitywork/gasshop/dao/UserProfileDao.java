package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.UserProfile;
import java.util.List;

/**
 *
 * @author redlongcity 28/10/2017
 */
public interface UserProfileDao {

    List<UserProfile> findAll();

    UserProfile findById(Integer id);

    UserProfile save(UserProfile profile);

    void delete(UserProfile profile);

    void update(UserProfile profile);

}
