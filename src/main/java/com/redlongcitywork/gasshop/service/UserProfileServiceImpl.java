package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.dao.UserProfileDao;
import com.redlongcitywork.gasshop.models.UserProfile;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author redlongcity 29/10/2017
 */
@Service("userProfileService")
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileDao dao;

    @Override
    public List<UserProfile> findAll() {
        return dao.findAll();
    }

    @Override
    public UserProfile findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public void saveUserProfile(UserProfile profile) {
        dao.save(profile);
    }

    @Override
    public void deleteUserProfile(UserProfile profile) {
        dao.delete(profile);
    }

    @Override
    public void updateUserProfile(UserProfile profile) {
        UserProfile entity = dao.findById(profile.getId());
        if (entity != null) {
            entity.setType(profile.getType());
        }
    }

    @Override
    public void deleteAll() {
        List<UserProfile> list = dao.findAll();
        if (list != null) {
            for (UserProfile profile : list) {
                dao.delete(profile);
            }
        }
    }

}
