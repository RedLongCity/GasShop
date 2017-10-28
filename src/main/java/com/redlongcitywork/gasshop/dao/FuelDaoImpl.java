package com.redlongcitywork.gasshop.dao;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.jdbcutils.ConnectionProvider;
import com.redlongcitywork.gasshop.jdbcutils.Transaction;
import com.redlongcitywork.gasshop.models.Fuel;
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
@Repository("fuelDao")
public class FuelDaoImpl implements FuelDao {

    @Autowired
    private Transaction tx;

    private static final Logger LOG = Logger.getLogger(FuelDaoImpl.class.getName());

    @Override
    public List<Fuel> findAll() {
        List<Fuel> list = new LinkedList<Fuel>();
        try {
            String query = "select * from fuels";
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
    public Fuel findById(Integer id) {
        checkNotNull(id);

        Fuel fuel = null;
        try {
            Connection connection = ConnectionProvider.getInstance().getConnection();
            String query = "select * from fuels where fuel_id= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                fuel = convert(set);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return fuel;
    }

    @Override
    public void save(Fuel fuel) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            String query = "insert into fuels (fuel_id, fuel_name) values (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, fuel.getId());
            statement.setString(2, fuel.getName());

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
    public void delete(Fuel fuel) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            String query = "delete from fuels where fuel_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, fuel.getId());
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
    public void update(Fuel fuel) {
        try {
            String query = "update fuels set fuel_name = ? where fuel_id= ?";

            PreparedStatement statement = tx.getConnection().
                    prepareStatement(query);
            statement.setString(1, fuel.getName());
            statement.setInt(2, fuel.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }

    private Fuel convert(ResultSet resultSet) {
        Fuel fuel = new Fuel();

        try {
            fuel.setId(resultSet.getInt("fuel_id"));
            fuel.setName(resultSet.getString("fuel_name"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return fuel;
    }

}