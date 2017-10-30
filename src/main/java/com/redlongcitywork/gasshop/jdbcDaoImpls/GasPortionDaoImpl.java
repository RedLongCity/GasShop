package com.redlongcitywork.gasshop.jdbcDaoImpls;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.GasPortionDao;
import com.redlongcitywork.gasshop.jdbcUtils.ConnectionProvider;
import com.redlongcitywork.gasshop.jdbcUtils.Transaction;
import com.redlongcitywork.gasshop.models.Fuel;
import com.redlongcitywork.gasshop.models.GasStation;
import com.redlongcitywork.gasshop.models.GasPortion;
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
 * @author redlongcity
 * 30/10/2017
 */
@Repository("gasPortionDao")
public class GasPortionDaoImpl implements GasPortionDao {

    private static final Logger LOG = Logger.getLogger(GasPortionDaoImpl.class.getName());

    @Autowired
    Transaction tx;
    
    private static final String SQL_SELECT_ALL_GAS_PORTIONS
            = "select * from gas_portions";

    private static final String SQL_SELECT_GAS_PORTION
            = "select * from gas_portions where gas_portion_id = ?";

    private static final String SQL_INSERT_GAS_PORTION
            = "insert into gas_portions"
            + " (amount, gas_stations_gas_station_id, fuels_id)"
            + " values(?, ?, ?)";

    private static final String SQL_UPDATE_GAS_PORTION
            = "update gas_portions set amount = ?, gas_stations_gas_station_id = ?,"
            + "fuels_id = ?, where gas_portion_id = ?";

    private static final String SQL_DELETE_GAS_PORTION
            = "delete from gas_portions wher gas_portion_id = ?";

    private static final String SQL_SELECT_GAS_STATION_BY_GAS_PORTION_ID
            = " select gs.* from gas_stations gs "
            + " inner join gas_portions gp on gp.gas_stations_gas_station_id = "
            + "gs.gas_station_id where gp.gas_portion_id = ?";

    private static final String SQL_SELECT_FUEL_BY_GAS_PORTION_ID
            = "select f.* from fuels f inner join gas_portions gp"
            + " on gp.fuels_id = f.fuel_id where gp.gas_portion_id = ?";

    @Override
    public List<GasPortion> findAll() {
        List<GasPortion> list = new LinkedList<GasPortion>();
        try {
            PreparedStatement statement = ConnectionProvider.getInstance().
                    getConnection().prepareStatement(SQL_SELECT_ALL_GAS_PORTIONS);
            ResultSet set = statement.executeQuery();
            while(set.next()){
                list.add(convert(set));
            }
            
            for(GasPortion portion:list){
                portion.setStation(findStationForGasPortion(portion));
                portion.setFuel(findFuelForGasPortion(portion));
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return list;
    }

    @Override
    public GasPortion findById(Integer id) {
        checkNotNull(id);
        
        GasPortion portion = null;
        try{
            Connection connection = ConnectionProvider.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_GAS_PORTION);
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                portion = convert(set);
            }
            portion.setStation(findStationForGasPortion(portion));
            portion.setFuel(findFuelForGasPortion(portion));
        }catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return portion;
    }

    @Override
    public void save(GasPortion portion) {
        Connection connection = tx.getConnection();
        
        try{
            tx.begin();
            
            PreparedStatement statement = connection.
                    prepareStatement(SQL_INSERT_GAS_PORTION);
            statement.setInt(1, portion.getAmount());
            statement.setInt(2, portion.getStation().getId());
            statement.setInt(3, portion.getFuel().getId());
            statement.setInt(4, portion.getId());
            
            statement.executeUpdate();
            tx.commit();
            
            portion.setId(getGasPortionId());
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
    public void delete(GasPortion portion) {
        Connection connection = tx.getConnection();
        try{
            tx.begin();
            
            PreparedStatement statement = connection.
                    prepareStatement(SQL_DELETE_GAS_PORTION);
            statement.setInt(1, portion.getId());
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
    public void update(GasPortion portion) {
        try{
            PreparedStatement statement = tx.getConnection().
                    prepareStatement(SQL_UPDATE_GAS_PORTION);
            statement.setInt(1,portion.getAmount());
            statement.setInt(2,portion.getStation().getId());
            statement.setInt(3,portion.getFuel().getId());
            statement.setInt(4,portion.getId());
            
            statement.executeUpdate();
        }catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }
    
    private Integer getGasPortionId(){
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

    private GasStation findStationForGasPortion(GasPortion portion){
        checkNotNull(portion);
        
        GasStation station = null;
        try{
            Connection connection = ConnectionProvider.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_GAS_STATION_BY_GAS_PORTION_ID);
            statement.setInt(1,portion.getId());
            ResultSet set = statement.executeQuery();
            if(set.next()){
                station = convertStation(set);
            }
        }catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return station;
    }
    
    private Fuel findFuelForGasPortion(GasPortion portion){
        checkNotNull(portion);
        
        Fuel fuel = null;
        try{
            Connection connection = ConnectionProvider.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_FUEL_BY_GAS_PORTION_ID);
            statement.setInt(1,portion.getId());
            ResultSet set = statement.executeQuery();
            if(set.next()){
                fuel = convertFuel(set);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return fuel;
    }
    
    private GasPortion convert(ResultSet set) {
        GasPortion portion = new GasPortion();

        try {
            portion.setId(set.getInt("gas_portion_id"));
            portion.setAmount(set.getInt("amount"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return portion;
    }
    
    private GasStation convertStation(ResultSet resultSet){
        GasStation station = new GasStation();
        
        try{
            station.setId(resultSet.getInt("gas_station_id"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return station;
    }
    
    private Fuel convertFuel(ResultSet resultSet){
        Fuel fuel = new Fuel();
        
        try{
            fuel.setId(resultSet.getInt("fuel_id"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return fuel;
    }
}
