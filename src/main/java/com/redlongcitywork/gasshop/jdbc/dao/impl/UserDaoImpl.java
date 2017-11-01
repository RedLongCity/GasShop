package com.redlongcitywork.gasshop.jdbc.dao.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.UserDao;
import com.redlongcitywork.gasshop.jdbc.utils.ConnectionProvider;
import com.redlongcitywork.gasshop.jdbc.utils.Transaction;
import com.redlongcitywork.gasshop.models.User;
import com.redlongcitywork.gasshop.models.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author redlongcity
 * 30/10/2017
 */
public class UserDaoImpl implements UserDao {

    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class.getName());

    @Autowired
    Transaction tx;
    
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
        List<User> list = new LinkedList<User>();
        try {
            PreparedStatement statement = ConnectionProvider.getInstance().
                    getConnection().prepareStatement(SQL_SELECT_ALL_USERS);
            ResultSet set = statement.executeQuery();
            while(set.next()){
                list.add(convert(set));
            }
            
            for(User user:list){
                user.setProfile(findProfileForUser(user));
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return list;
    }

    @Override
    public User findById(Integer id) {
        checkNotNull(id);
        
        User user = null;
        try{
            Connection connection = ConnectionProvider.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_USER);
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                user = convert(set);
            }
            user.setProfile(findProfileForUser(user));
        }catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return user;
    }

    @Override
    public User save(User user) {
        Connection connection = tx.getConnection();
        
        try{
            tx.begin();
            
            PreparedStatement statement = connection.
                    prepareStatement(SQL_INSERT_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getTel());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getPassword());
            statement.setInt(7, user.getProfile().getId());
            
            statement.executeUpdate();
            tx.commit();
            
            user.setId(getUserId());
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.log(Level.WARNING, e.getMessage());
            }
        }
        return user;
    }

    @Override
    public void delete(User user) {
        Connection connection = tx.getConnection();
        try{
            tx.begin();
            
            PreparedStatement statement = connection.
                    prepareStatement(SQL_DELETE_USER);
            statement.setInt(1, user.getId());
            statement.executeUpdate();
            
            tx.commit();
        }catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.log(Level.WARNING, e.getMessage());
            }
        }
    }

    @Override
    public void update(User user) {
        try{
            PreparedStatement statement = tx.getConnection().
                    prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getTel());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getPassword());
            statement.setInt(7, user.getProfile().getId());
            
            statement.executeUpdate();
        }catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }
    
    private Integer getUserId(){
        Integer id = null;
        try{
            Connection connection = ConnectionProvider.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement("select LAST_INSERT_ID()");
            ResultSet set = statement.executeQuery();
            if(set.next()){
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return id;
    }

    private UserProfile findProfileForUser(User user){
        checkNotNull(user);
        
        UserProfile profile = null;
        try{
            Connection connection = ConnectionProvider.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_USER_PROFILE_BY_USER_ID);
            statement.setInt(1,user.getId());
            ResultSet set = statement.executeQuery();
            if(set.next()){
                profile = convertProfile(set);
            }
        }catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return profile;
    }
    
    private User convert(ResultSet set) {
        User user = new User();

        try {
            user.setId(set.getInt("user_id"));
            user.setFirstName(set.getString("first_name"));
            user.setLastName(set.getString("last_name"));
            user.setEmail(set.getString("email"));
            user.setTel(set.getString("tel"));
            user.setPassword(set.getString("password"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return user;
    }
    
    private UserProfile convertProfile(ResultSet resultSet){
        UserProfile profile = new UserProfile();
        
        try{
            profile.setId(resultSet.getInt("user_profiles_id"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return profile;
    }
}
