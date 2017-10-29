package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.models.UserProfile;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface UserProfileService {

    List<UserProfile> findAll();

    UserProfile findById(Integer id);

    void saveUserProfile(UserProfile profile);

    void deleteUserProfile(UserProfile profile);

    void updateUserProfile(UserProfile profile);

    void deleteAll();

}
