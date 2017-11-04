package com.redlongcitywork.gasshop.template.utils;

import com.redlongcitywork.gasshop.models.Reference;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author redlongcity
 * 30/10/2017
 */
public class ReferenceExtractor implements ResultSetExtractor<Reference> {

    @Override
    public Reference extractData(ResultSet rs) throws SQLException, DataAccessException {
        Reference reference = new Reference();
        
        reference.setId(rs.getInt("reference_id"));
        reference.setCost(rs.getFloat("cost"));
        return reference;
    }
    
}
