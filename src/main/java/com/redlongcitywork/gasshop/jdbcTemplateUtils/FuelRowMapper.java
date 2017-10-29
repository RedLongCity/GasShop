package com.redlongcitywork.gasshop.jdbcTemplateUtils;

import com.redlongcitywork.gasshop.models.Fuel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author redlongcity
 * 29/10/2017
 */
public class FuelRowMapper implements RowMapper<Fuel> {

    @Override
    public Fuel mapRow(ResultSet rs, int i) throws SQLException {
        FuelExtractor extractor = new FuelExtractor();
        return extractor.extractData(rs);
    }
    
}
