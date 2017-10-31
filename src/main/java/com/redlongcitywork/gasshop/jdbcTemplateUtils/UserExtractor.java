package com.redlongcitywork.gasshop.jdbcTemplateUtils;

import com.redlongcitywork.gasshop.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author redlongcity
 * 31/10/2017
 */
public class UserExtractor implements ResultSetExtractor<User> {

    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setTel(rs.getString("tel"));
        user.setAddress(rs.getString("address"));
        user.setPassword(rs.getString("password"));
        
        return user;
    }
    
}
