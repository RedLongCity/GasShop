package com.redlongcitywork.gasshop.jdbcTemplateUtils;

import com.redlongcitywork.gasshop.models.UserProfile;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author redlongcity
 * 29/10/2017
 */
public class UserProfileRowMapper implements RowMapper<UserProfile> {

    @Override
    public UserProfile mapRow(ResultSet rs, int i) throws SQLException {
        UserProfileExtractor extractor = new UserProfileExtractor();
        return extractor.extractData(rs);
    }
    
}
