package com.redlongcitywork.gasshop.jdbcDaoImpls;

import com.redlongcitywork.gasshop.dao.GasStationDao;
import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.jdbcUtils.ConnectionProvider;
import com.redlongcitywork.gasshop.jdbcUtils.Transaction;
import com.redlongcitywork.gasshop.models.GasStation;
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
@Repository("gasStationDao")
public class GasStationImpl implements GasStationDao {

    @Autowired
    private Transaction tx;

    private static final Logger LOG = Logger.getLogger(GasStationImpl.class.getName());

    @Override
    public List<GasStation> findAll() {
        List<GasStation> list = new LinkedList<GasStation>();
        try {
            String query = "select * from gas_stations";
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
    public GasStation findById(Integer id) {
        checkNotNull(id);

        GasStation station = null;
        try {
            Connection connection = ConnectionProvider.getInstance().
                    getConnection();
            String query = "select * from fuels where gas_station_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                station = convert(set);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return station;
    }

    @Override
    public void save(GasStation station) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            String query = "insert into gas_sations (gas_station_id, gas_station_name) values(?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, station.getId());
            statement.setString(2, station.getName());

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
    public void delete(GasStation station) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            String query = "delete from gas_stations where gas_station_id= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, station.getId());
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
    public void update(GasStation station) {
        try {
            String query = "update gas_stations set gas_station_name= ? where gas_station_id = ?";
            PreparedStatement statement = tx.getConnection().
                    prepareStatement(query);
            statement.setString(1, station.getName());
            statement.setInt(2, station.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }

    private GasStation convert(ResultSet set) {
        GasStation station = new GasStation();

        try {
            station.setId(set.getInt("gas_station_id"));
            station.setName(set.getString("gas_station_name"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return station;
    }

}
