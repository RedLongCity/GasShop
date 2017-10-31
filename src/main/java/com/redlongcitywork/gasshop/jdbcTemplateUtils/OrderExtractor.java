package com.redlongcitywork.gasshop.jdbcTemplateUtils;

import com.redlongcitywork.gasshop.models.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author redlongcity
 * 31/10/2017
 */
public class OrderExtractor implements ResultSetExtractor<Order> {

    @Override
    public Order extractData(ResultSet rs) throws SQLException, DataAccessException {
        Order order = new Order();
        
        order.setId(rs.getInt("order_id"));
        order.setDate(rs.getDate("date"));
        order.setStatus(rs.getString("status"));
        return order;
    }
    
}
