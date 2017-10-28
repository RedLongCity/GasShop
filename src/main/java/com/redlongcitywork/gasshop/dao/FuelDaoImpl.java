package com.redlongcitywork.gasshop.dao;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.jdbcutils.ConnectionProvider;
import com.redlongcitywork.gasshop.jdbcutils.Transaction;
import com.redlongcitywork.gasshop.jdbcutils.TransactionImpl;
import com.redlongcitywork.gasshop.models.Fuel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author redlongcity 28/10/2017
 */
@Repository("fuelDao")
public class FuelDaoImpl implements FuelDao {

    private static final Logger LOG = Logger.getLogger(FuelDaoImpl.class.getName());

    @Override
    public List<Fuel> findAll() {
        List<Fuel> list = new LinkedList<Fuel>();
        try {
            String query = "select from fuels";
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
            String query = "select * from fuels where id= ?";
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
        Transaction tx = TransactionImpl.getInstance();
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            String query = "insert into fuels (id, name) values (?, ?)";
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
        Transaction tx = TransactionImpl.getInstance();
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            String query = "delete from fuels where id = ?";
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
            String query = "update fuels set name = ?";

            PreparedStatement statement = TransactionImpl.getInstance().
                    getConnection().prepareStatement(query);
            statement.setString(1, fuel.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }

    private Fuel convert(ResultSet resultSet) {
        Fuel fuel = new Fuel();

        try {
            fuel.setId(resultSet.getInt("id"));
            fuel.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return fuel;
    }

}
