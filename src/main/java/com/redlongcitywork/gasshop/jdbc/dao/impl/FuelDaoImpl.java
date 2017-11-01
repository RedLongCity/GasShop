package com.redlongcitywork.gasshop.jdbc.dao.impl;

import com.redlongcitywork.gasshop.dao.FuelDao;
import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.jdbc.utils.ConnectionProvider;
import com.redlongcitywork.gasshop.jdbc.utils.Transaction;
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

    private static final String SQL_SELECT_FUELS
            = "select * from fuels";

    private static final String SQL_SELECT_FUEL
            = "select * from fuels where fuel_id = ?";

    private static final String SQL_INSERT_FUEL
            = "insert into fuels (fuel_name) values(?)";

    private static final String SQL_DELETE_FUEL
            = "delete from fuels where fuel_id = ?";

    private static final String SQL_DELETE_FUEL_GAS_PORTIONS
            = "delete from gas_portions where fuels_id = ?";

    private static final String SQL_DELETE_FUEL_REFERENCES
            = "delete from references where fuels_id = ?";

    private static final String SQL_UPDATE_FUEL
            = "update fuels set fuel_name = ?"
            + " where fuel_id = ?";

    @Override
    public List<Fuel> findAll() {
        List<Fuel> list = new LinkedList<Fuel>();
        try {
            PreparedStatement statement = ConnectionProvider.getInstance().
                    getConnection().prepareStatement(SQL_SELECT_FUELS);
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
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_FUEL);
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
    public Fuel save(Fuel fuel) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            PreparedStatement statement = connection.
                    prepareStatement(SQL_INSERT_FUEL);
            statement.setString(1, fuel.getName());

            statement.executeUpdate();

            statement = connection.prepareStatement("select last_insert_id()");
            ResultSet set = statement.executeQuery();
            fuel.setId(set.getInt(1));
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
        return fuel;
    }

    @Override
    public void delete(Fuel fuel) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            PreparedStatement statement = connection.
                    prepareStatement(SQL_DELETE_FUEL_GAS_PORTIONS);
            statement.setInt(1, fuel.getId());
            statement.executeUpdate();
            
            statement = connection.
                    prepareStatement(SQL_DELETE_FUEL_REFERENCES);
            statement.setInt(1, fuel.getId());
            statement.executeUpdate();
            
            statement = connection.
                    prepareStatement(SQL_DELETE_FUEL);
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
            PreparedStatement statement = tx.getConnection().
                    prepareStatement(SQL_UPDATE_FUEL);
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
