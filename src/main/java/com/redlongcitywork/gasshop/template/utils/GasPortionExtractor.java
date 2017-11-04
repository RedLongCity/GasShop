package com.redlongcitywork.gasshop.template.utils;

import com.redlongcitywork.gasshop.models.GasPortion;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author redlongcity 30/10/2017
 */
public class GasPortionExtractor implements ResultSetExtractor<GasPortion> {

    @Override
    public GasPortion extractData(ResultSet set) throws SQLException, DataAccessException {
        GasPortion portion = new GasPortion();

        portion.setId(set.getInt("gas_portions_id"));
        portion.setAmount(set.getInt("amount"));

        return portion;
    }

}
