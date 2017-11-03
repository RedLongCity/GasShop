package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.dao.UserDao;
import com.redlongcitywork.gasshop.models.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author redlongcity 29/10/2017
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public User findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public void saveUser(User user) {
        dao.save(user);
    }

    @Override
    public void deleteUser(User user) {
        dao.delete(user);
    }

    @Override
    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if (entity != null) {
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
            entity.setTel(user.getTel());
            entity.setAddress(user.getAddress());
            entity.setPassword(user.getPassword());
            entity.setProfile(user.getProfile());
        }
    }

    @Override
    public void deleteAll() {
        List<User> list = dao.findAll();
        if (list != null) {
            for (User user : list) {
                dao.delete(user);
            }
        }
    }
}
