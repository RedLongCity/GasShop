package com.redlongcitywork.gasshop.dao.jdbc.template.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.UserProfileDao;
import com.redlongcitywork.gasshop.jdbc.template.utils.UserProfileRowMapper;
import com.redlongcitywork.gasshop.models.UserProfile;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author redlongcity 29/10/2017
 */
@Repository("userProfileDao")
public class UserProfileDaoJdbcTemplateImpl implements UserProfileDao {

    private static final Logger LOG = Logger.getLogger(UserProfileDaoJdbcTemplateImpl.class.getName());

    @Autowired
    private JdbcTemplate template;

    private static final String SQL_SELECT_USER_PROFILES
            = "select * from user_profiles";

    private static final String SQL_SELECT_USER_PROFILE
            = "select * from user_profiles where user_profiles_id= ?";

    private static final String SQL_INSERT_USER_PROFILE
            = "insert into user_profiles (user_profile_type)"
            + " valuest(?)";

    private static final String SQL_DELETE_USER_PROFILE
            = "delete from user_profiles where user_profile_id = ?";

    private static final String SQL_DELETE_USER_PROFILE_USERS
            = "delete from users where user_profiles_user_profiles_id = ?";

    private static final String SQL_UPDATE_USER_PROFILE
            = "update user_profiles set user_profile_type = ?"
            + " where user_profile_id = ?";

    @Override
    public List<UserProfile> findAll() {
        List<UserProfile> list;
        list = template.query(SQL_SELECT_USER_PROFILES, new UserProfileRowMapper());
        checkNotNull(list);
        return list;
    }

    @Override
    public UserProfile findById(Integer id) {
        checkNotNull(id);
        return template.queryForObject(SQL_SELECT_USER_PROFILE,
                new UserProfileRowMapper(),
                id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public UserProfile save(UserProfile profile) {
        checkNotNull(profile);
        template.update(SQL_INSERT_USER_PROFILE,
                profile.getType());
        profile.setId(template.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        return profile;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void delete(UserProfile profile) {
        checkNotNull(profile);
        template.update(SQL_DELETE_USER_PROFILE_USERS,
                profile.getId());
        template.update(SQL_DELETE_USER_PROFILE,
                profile.getId());
    }

    @Override
    public void update(UserProfile profile) {
        checkNotNull(profile);
        template.update(SQL_UPDATE_USER_PROFILE,
                profile.getType(),
                profile.getId());
    }

}
