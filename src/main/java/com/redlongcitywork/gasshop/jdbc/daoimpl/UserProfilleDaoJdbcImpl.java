package com.redlongcitywork.gasshop.jdbc.daoimpl;

import com.redlongcitywork.gasshop.dao.UserProfileDao;
import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.jdbc.utils.ConnectionProvider;
import com.redlongcitywork.gasshop.jdbc.utils.Transaction;
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
import org.springframework.stereotype.Repository;

/**
 *
 * @author redlongcity 28/10/2017
 */
//@Repository("userProfileDao")
public class UserProfilleDaoJdbcImpl implements UserProfileDao {

    private static final Logger LOG = Logger.getLogger(UserProfilleDaoJdbcImpl.class.getName());

    @Autowired
    private Transaction tx;

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
        List<UserProfile> list = new LinkedList<UserProfile>();
        try {
            PreparedStatement statement = ConnectionProvider.getInstance().
                    getConnection().prepareStatement(SQL_SELECT_USER_PROFILES);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                list.add(convert(set));
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return list;
    }

    @Override
    public UserProfile findById(Integer id) {
        checkNotNull(id);

        UserProfile profile = null;
        try {
            Connection connection = ConnectionProvider.getInstance().getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_USER_PROFILE);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                profile = convert(set);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return profile;
    }

    @Override
    public UserProfile save(UserProfile profile) {
        Connection connection = tx.getConnection();
        try {
            tx.begin();

            PreparedStatement statement = connection.
                    prepareStatement(SQL_INSERT_USER_PROFILE);
            statement.setString(1, profile.getType());
            statement.executeUpdate();

            statement = connection.prepareStatement("select last_insert_id");
            ResultSet set = statement.executeQuery();
            profile.setId(set.getInt(1));
            tx.commit();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.log(Level.WARNING, e.getMessage());
            }
        }
        return profile;
    }

    @Override
    public void delete(UserProfile profile) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            PreparedStatement statement = connection.
                    prepareStatement(SQL_DELETE_USER_PROFILE_USERS);
            statement.setInt(1, profile.getId());
            statement.executeUpdate();

            statement = connection.
                    prepareStatement(SQL_DELETE_USER_PROFILE);
            statement.setInt(1, profile.getId());
            statement.executeUpdate();

            tx.commit();
        } catch (SQLException e) {
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
    public void update(UserProfile profile) {
        try {
            PreparedStatement statement = tx.getConnection().
                    prepareStatement(SQL_UPDATE_USER_PROFILE);
            statement.setString(1, profile.getType());
            statement.setInt(2, profile.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }

    private UserProfile convert(ResultSet set) {
        UserProfile profile = new UserProfile();
        try {
            profile.setId(set.getInt("user_profiles_id"));
            profile.setType(set.getString("user_profiles_type"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return profile;
    }

}
