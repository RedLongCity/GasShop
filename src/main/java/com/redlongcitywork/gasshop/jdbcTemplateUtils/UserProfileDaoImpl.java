package com.redlongcitywork.gasshop.jdbcTemplateUtils;

import com.redlongcitywork.gasshop.dao.UserProfileDao;
import com.redlongcitywork.gasshop.models.UserProfile;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author redlongcity 29/10/2017
 */
@Repository("userProfileDao")
public class UserProfileDaoImpl implements UserProfileDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<UserProfile> findAll() {
        List<UserProfile> list;
        String query = "select * from user_profiles";
        list = template.query(query, new UserProfileRowMapper());
        checkNotNull(list);
        return list;
    }

    @Override
    public UserProfile findById(Integer id) {
        checkNotNull(id);
        List<UserProfile> list;
        String query = "select * from user_profiles where user_profiles_id=" + id;
        list = template.query(query, new UserProfileRowMapper());
        checkNotNull(list);
        return list.get(0);
    }

    @Override
    public void save(UserProfile profile) {
        checkNotNull(profile);
        String query = "insert into user_profiles (user_profiles_id, user_profile_type)"
                + " valuest(?, ?)";
        template.update(query, new Object[]{profile.getId(), profile.getType()});
    }

    @Override
    public void delete(UserProfile profile) {
        checkNotNull(profile);
        String query = "delete from user_profiles where user_profile_id=" + profile.getId();
        template.update(query);
    }

    @Override
    public void update(UserProfile profile) {
        checkNotNull(profile);
        String query = "update user_profiles set user_profile_type = ?"
                + " where user_profile_id = ?";
        template.update(query, new Object[]{profile.getType(), profile.getId()});
    }

}
