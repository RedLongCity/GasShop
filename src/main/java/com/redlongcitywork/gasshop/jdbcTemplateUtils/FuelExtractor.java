package com.redlongcitywork.gasshop.jdbcTemplateUtils;

import com.redlongcitywork.gasshop.models.Fuel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author redlongcity 29/10/2017
 */
public class FuelExtractor implements ResultSetExtractor<Fuel> {

    @Override
    public Fuel extractData(ResultSet set) throws SQLException, DataAccessException {
        Fuel fuel = new Fuel();

        fuel.setId(set.getInt("fuel_id"));
        fuel.setName(set.getString("fuel_name"));

        return fuel;
    }

}
