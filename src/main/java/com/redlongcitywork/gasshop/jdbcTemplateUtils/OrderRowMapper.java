package com.redlongcitywork.gasshop.jdbcTemplateUtils;

import com.redlongcitywork.gasshop.models.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author redlongcity
 * 31/10/2017
 */
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int i) throws SQLException {
        OrderExtractor extractor = new OrderExtractor();
        return extractor.extractData(rs);
    }
    
}
