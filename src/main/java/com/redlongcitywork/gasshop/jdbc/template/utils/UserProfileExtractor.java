package com.redlongcitywork.gasshop.jdbc.template.utils;

import com.redlongcitywork.gasshop.models.UserProfile;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author redlongcity 29/10/2017
 */
public class UserProfileExtractor implements ResultSetExtractor<UserProfile> {

    @Override
    public UserProfile extractData(ResultSet set) throws SQLException, DataAccessException {
        UserProfile profile = new UserProfile();

        profile.setId(set.getInt("user_profiles_id"));
        profile.setType(set.getString("user_profile_type"));

        return profile;
    }

}
