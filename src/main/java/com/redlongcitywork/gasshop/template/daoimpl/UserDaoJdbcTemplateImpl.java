package com.redlongcitywork.gasshop.template.daoimpl;

import com.redlongcitywork.gasshop.dao.UserDao;
import com.redlongcitywork.gasshop.template.utils.UserProfileRowMapper;
import com.redlongcitywork.gasshop.template.utils.UserRowMapper;
import com.redlongcitywork.gasshop.models.User;
import com.redlongcitywork.gasshop.models.UserProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author redlongcity 31/10/2017
 */
@Repository("userDao")
public class UserDaoJdbcTemplateImpl implements UserDao {

    private static final Logger LOG = Logger.getLogger(UserDaoJdbcTemplateImpl.class.getName());

    @Autowired
    private JdbcTemplate template;

    private static final String SQL_SELECT_ALL_USERS
            = "select * from users";

    private static final String SQL_SELECT_USER
            = "select * from users where user_id = ?";

    private static final String SQL_INSERT_USER
            = "insert into users"
            + " (first_name, last_name, email, tel, address, password, "
            + "user_profiles_user_profiles_id)"
            + " values(?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_USER
            = "update users set first_name = ?, last_name = ?, email = ?"
            + ", tel = ?, address = ?"
            + "password = ?, user_profiles_user_profiles_id = ?,"
            + " where user_id = ?";

    private static final String SQL_DELETE_USER
            = "delete from users wher user_id = ?";

    private static final String SQL_SELECT_USER_PROFILE_BY_USER_ID
            = "select p.* from profiles p inner join users u"
            + " on p.user_profiles_id = u.user_profiles_user_profiles_id where u.user_id = ?";

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<User>();
        list = template.query(SQL_SELECT_ALL_USERS,
                new UserRowMapper());
        for (User user : list) {
            user.setProfile(findProfileForUser(user));
        }
        return list;
    }

    @Override
    public User findById(Integer id) {
        User user = new User();

        user = (User) template.query(SQL_SELECT_USER,
                new UserRowMapper(),
                id);
        user.setProfile(findProfileForUser(user));
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User save(User user) {
        template.update(SQL_INSERT_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getTel(),
                user.getAddress(),
                user.getPassword(),
                user.getProfile().getId());

        user.setId(template.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(User user) {
        template.update(SQL_DELETE_USER,
                user.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void update(User user) {
        template.update(SQL_UPDATE_USER,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getTel(),
                user.getAddress(),
                user.getPassword(),
                user.getProfile().getId(),
                user.getId());
    }

    private UserProfile findProfileForUser(User user) {
        return (UserProfile) template.query(SQL_SELECT_USER_PROFILE_BY_USER_ID,
                new UserProfileRowMapper(),
                user.getId());
    }
}
