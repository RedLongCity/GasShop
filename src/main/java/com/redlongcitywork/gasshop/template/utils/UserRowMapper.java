package com.redlongcitywork.gasshop.template.utils;

import com.redlongcitywork.gasshop.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author redlongcity
 * 31/10/2017
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        UserExtractor extractor = new UserExtractor();
        return extractor.extractData(rs);
    }
    
}
