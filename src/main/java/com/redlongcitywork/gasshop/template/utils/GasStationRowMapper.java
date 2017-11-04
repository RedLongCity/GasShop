package com.redlongcitywork.gasshop.template.utils;

import com.redlongcitywork.gasshop.models.GasStation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author redlongcity 29/10/2017
 */
public class GasStationRowMapper implements RowMapper<GasStation> {

    @Override
    public GasStation mapRow(ResultSet rs, int i) throws SQLException {
        GasStationExtractor extractor = new GasStationExtractor();
        return extractor.extractData(rs);
    }

}
