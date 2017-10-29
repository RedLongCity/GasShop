package com.redlongcitywork.gasshop.jdbcDaoImpls;

import com.redlongcitywork.gasshop.dao.UserProfileDao;
import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.jdbcUtils.ConnectionProvider;
import com.redlongcitywork.gasshop.jdbcUtils.Transaction;
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
@Repository("userProfileDao")
public class UserProfilleDaoImpl implements UserProfileDao {

    private static final Logger LOG = Logger.getLogger(UserProfilleDaoImpl.class.getName());

    @Autowired
    private Transaction tx;

    @Override
    public List<UserProfile> findAll() {
        List<UserProfile> list = new LinkedList<UserProfile>();
        try {
            String query = "select * from user_profiles";
            PreparedStatement statement = ConnectionProvider.getInstance().
                    getConnection().prepareStatement(query);
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
            String query = "select * from user_profiles where user_profiles_id= ?";
            PreparedStatement statement = connection.prepareStatement(query);
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
    public void save(UserProfile profile) {
        Connection connection = tx.getConnection();
        try {
            tx.begin();

            String query = "insert into user_profiles (user_profiles_id, user_profiles_type) values (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, profile.getId());
            statement.setString(2, profile.getType());
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
    public void delete(UserProfile profile) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            String query = "delete user_profiles where user_profiles_id= ?";
            PreparedStatement statement = connection.prepareStatement(query);
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
        try{
            String query = "update user_profiles set user_profiles_type= ?, where user_profiles_id= ?";
            PreparedStatement statement = tx.getConnection().
                    prepareStatement(query);
            statement.setString(1, profile.getType());
            statement.setInt(2,profile.getId());
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
