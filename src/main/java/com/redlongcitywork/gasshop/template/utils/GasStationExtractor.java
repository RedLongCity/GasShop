package com.redlongcitywork.gasshop.template.utils;

import com.redlongcitywork.gasshop.models.GasStation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author redlongcity
 * 29/10/2017
 */
public class GasStationExtractor implements ResultSetExtractor<GasStation>{

    @Override
    public GasStation extractData(ResultSet set) throws SQLException, DataAccessException {
        GasStation station = new GasStation();
        
        station.setId(set.getInt("gas_station_id"));
        station.setName(set.getString("gas_station_name"));
        
        return station;
    }
    
}
