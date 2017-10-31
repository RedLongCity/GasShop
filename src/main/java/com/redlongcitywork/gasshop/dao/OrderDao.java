package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.Order;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface OrderDao {

    List<Order> findAll();

    Order findById(Integer id);

    Order save(Order order);

    void delete(Order order);

    void update(Order order);

}
