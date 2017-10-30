package com.redlongcitywork.gasshop.jdbcTemplateUtils;

import com.redlongcitywork.gasshop.models.GasPortion;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author redlongcity
 * 30/10/2017
 */
public class GasPortionRowMapper implements RowMapper<GasPortion>{

    @Override
    public GasPortion mapRow(ResultSet rs, int i) throws SQLException {
        GasPortionExtractor extractor = new GasPortionExtractor();
        return extractor.extractData(rs);
    }
    
}
