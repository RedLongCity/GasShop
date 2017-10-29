package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.models.Order;
import java.util.List;

/**
 *
 * @author redlongcity 29/10/2017
 */
public interface OrderService {

    List<Order> findAll();

    Order findById(Integer id);

    void saveOrder(Order order);

    void deleteOrder(Order order);

    void updateOrder(Order order);

    void deleteAll();

}
