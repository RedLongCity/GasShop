package com.redlongcitywork.gasshop.template.utils;

import com.redlongcitywork.gasshop.models.Reference;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author redlongcity 30/10/2017
 */
public class ReferenceRowMapper implements RowMapper<Reference> {

    @Override
    public Reference mapRow(ResultSet rs, int i) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
