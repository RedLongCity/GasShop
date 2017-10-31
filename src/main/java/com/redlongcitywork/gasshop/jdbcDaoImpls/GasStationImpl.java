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

    private static final String SQL_SELECT_GAS_STATIONS
            = "select * from gas_stations";

    private static final String SQL_SELECT_GAS_STATION
            = "select * from gas_stations where gas_station_id = ?";

    private static final String SQL_INSERT_GAS_STATION
            = "insert into gas_stations (gas_station_name)"
            + " values(?)";

    private static final String SQL_DELETE_GAS_STATION
            = "delete from gas_stations where gas_station_id = ?";

    private static final String SQL_DELETE_GAS_STATION_GAS_PORTIONS
            = "delete from gas_portions where gas_stations_gas_station_id = ?";

    private static final String SQL_DELETE_GAS_STATION_REFERENCES
            = "delete from references where gas_stations_gas_station_id = ?";

    private static final String SQL_UPDATE_GAS_STATION
            = "update gas_station set gas_station_name = ?"
            + " where gas_station_id = ?";

    @Override
    public List<GasStation> findAll() {
        List<GasStation> list = new LinkedList<GasStation>();
        try {
            PreparedStatement statement = ConnectionProvider.getInstance().
                    getConnection().prepareStatement(SQL_SELECT_GAS_STATIONS);
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
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_GAS_STATION);
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
    public GasStation save(GasStation station) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            PreparedStatement statement = connection.
                    prepareStatement(SQL_INSERT_GAS_STATION);
            statement.setString(1, station.getName());

            statement.executeUpdate();

            statement = connection.prepareStatement("select last_insert_id()");
            ResultSet set = statement.executeQuery();
            station.setId(set.getInt(1));
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
        return station;
    }

    @Override
    public void delete(GasStation station) {
        Connection connection = tx.getConnection();

        try {
            tx.begin();

            PreparedStatement statement = connection.
                    prepareStatement(SQL_DELETE_GAS_STATION_GAS_PORTIONS);
            statement.setInt(1, station.getId());
            statement.executeUpdate();

            statement = connection.
                    prepareStatement(SQL_DELETE_GAS_STATION_REFERENCES);
            statement.setInt(1, station.getId());
            statement.executeUpdate();

            statement = connection.
                    prepareStatement(SQL_DELETE_GAS_STATION);
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
            PreparedStatement statement = tx.getConnection().
                    prepareStatement(SQL_UPDATE_GAS_STATION);
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
