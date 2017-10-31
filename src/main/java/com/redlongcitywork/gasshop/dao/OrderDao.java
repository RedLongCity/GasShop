package com.redlongcitywork.gasshop.dao;

import com.redlongcitywork.gasshop.models.Order;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface OrderDao extends AbstractDao<Order> {

    @Override
    List<Order> findAll();

    @Override
    Order findById(Integer id);

    @Override
    Order save(Order order);

    @Override
    void delete(Order order);

    @Override
    void update(Order order);

}
