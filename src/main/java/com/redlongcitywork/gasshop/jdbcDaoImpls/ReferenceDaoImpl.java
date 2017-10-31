package com.redlongcitywork.gasshop.jdbcDaoImpls;

import static com.google.common.base.Preconditions.checkNotNull;
import com.redlongcitywork.gasshop.dao.ReferenceDao;
import com.redlongcitywork.gasshop.jdbcUtils.ConnectionProvider;
import com.redlongcitywork.gasshop.jdbcUtils.Transaction;
import com.redlongcitywork.gasshop.models.Fuel;
import com.redlongcitywork.gasshop.models.GasStation;
import com.redlongcitywork.gasshop.models.Reference;
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
 * @author redlongcity 30/10/2017
 */
@Repository("referenceDao")
public class ReferenceDaoImpl implements ReferenceDao {

    private static final Logger LOG = Logger.getLogger(ReferenceDaoImpl.class.getName());

    @Autowired
    Transaction tx;

    private static final String SQL_SELECT_ALL_REFERENCES
            = "select * from references";

    private static final String SQL_SELECT_REFERENCE
            = "select * from references where reference_id = ?";

    private static final String SQL_INSERT_REFERENCE
            = "insert into references"
            + " (cost, gas_stations_gas_station_id, fuels_id)"
            + " values(?, ?, ?)";

    private static final String SQL_UPDATE_REFERENCE
            = "update references set cost = ?, gas_stations_gas_station_id = ?,"
            + "fuels_id = ?, where reference_id = ?";

    private static final String SQL_DELETE_REFERENCE
            = "delete from references wher reference_id = ?";

    private static final String SQL_SELECT_GAS_STATION_BY_REFERENCE_ID
            = " select gs.* from gas_stations gs "
            + " inner join references ref on ref.gas_stations_gas_station_id = "
            + "gs.gas_station_id where ref.reference_id = ?";

    private static final String SQL_SELECT_FUEL_BY_REFERENCE_ID
            = "select f.* from fuels f inner join references ref"
            + " on ref.fuels_id = f.fuel_id where ref.reference_id = ?";

    @Override
    public List<Reference> findAll() {
        List<Reference> list = new LinkedList<Reference>();
        try {
            PreparedStatement statement = ConnectionProvider.getInstance().
                    getConnection().prepareStatement(SQL_SELECT_ALL_REFERENCES);
            ResultSet set = statement.executeQuery();
            while(set.next()){
                list.add(convert(set));
            }
            
            for(Reference reference:list){
                reference.setStation(findStationForReference(reference));
                reference.setFuel(findFuelForReference(reference));
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return list;
    }

    @Override
    public Reference findById(Integer id) {
        checkNotNull(id);
        
        Reference reference = null;
        try{
            Connection connection = ConnectionProvider.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_REFERENCE);
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()){
                reference = convert(set);
            }
            reference.setStation(findStationForReference(reference));
            reference.setFuel(findFuelForReference(reference));
        }catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return reference;
    }

    @Override
    public Reference save(Reference reference) {
        Connection connection = tx.getConnection();
        
        try{
            tx.begin();
            
            PreparedStatement statement = connection.
                    prepareStatement(SQL_INSERT_REFERENCE);
            statement.setFloat(1, reference.getCost());
            statement.setInt(2, reference.getStation().getId());
            statement.setInt(3, reference.getFuel().getId());
            statement.setInt(4, reference.getId());
            
            statement.executeUpdate();
            tx.commit();
            
            reference.setId(getReferenceId());
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.log(Level.WARNING, e.getMessage());
            }
        }
        return reference;
    }

    @Override
    public void delete(Reference reference) {
        Connection connection = tx.getConnection();
        try{
            tx.begin();
            
            PreparedStatement statement = connection.
                    prepareStatement(SQL_DELETE_REFERENCE);
            statement.setInt(1, reference.getId());
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
    public void update(Reference reference) {
        try{
            PreparedStatement statement = tx.getConnection().
                    prepareStatement(SQL_UPDATE_REFERENCE);
            statement.setFloat(1,reference.getCost());
            statement.setInt(2,reference.getStation().getId());
            statement.setInt(3,reference.getFuel().getId());
            statement.setInt(4,reference.getId());
            
            statement.executeUpdate();
        }catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
    }
    
    private Integer getReferenceId(){
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

    private GasStation findStationForReference(Reference reference){
        checkNotNull(reference);
        
        GasStation station = null;
        try{
            Connection connection = ConnectionProvider.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_GAS_STATION_BY_REFERENCE_ID);
            statement.setInt(1,reference.getId());
            ResultSet set = statement.executeQuery();
            if(set.next()){
                station = convertStation(set);
            }
        }catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return station;
    }
    
    private Fuel findFuelForReference(Reference reference){
        checkNotNull(reference);
        
        Fuel fuel = null;
        try{
            Connection connection = ConnectionProvider.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(SQL_SELECT_FUEL_BY_REFERENCE_ID);
            statement.setInt(1,reference.getId());
            ResultSet set = statement.executeQuery();
            if(set.next()){
                fuel = convertFuel(set);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return fuel;
    }
    
    private Reference convert(ResultSet set) {
        Reference reference = new Reference();

        try {
            reference.setId(set.getInt("reference_id"));
            reference.setCost(set.getFloat("cost"));
        } catch (SQLException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return reference;
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
