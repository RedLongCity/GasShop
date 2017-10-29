package com.redlongcitywork.gasshop.service;

import com.redlongcitywork.gasshop.dao.OrderDao;
import com.redlongcitywork.gasshop.models.Order;
import com.redlongcitywork.gasshop.models.Order;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author redlongcity
 * 29/10/2017
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao dao;

    @Override
    public List<Order> findAll() {
        return dao.findAll();
    }

    @Override
    public Order findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public void saveOrder(Order order) {
        dao.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        dao.delete(order);
    }

    @Override
    public void updateOrder(Order order) {
        Order entity = dao.findById(order.getId());
        if (entity != null) {
            entity.setDate(order.getDate());
            entity.setStatus(order.getStatus());
            entity.setGasPortionSet(order.getGasPortionSet());
            entity.setUser(order.getUser());
        }
    }

    @Override
    public void deleteAll() {
        List<Order> list = dao.findAll();
        if (list != null) {
            for (Order order : list) {
                dao.delete(order);
            }
        }
    }
    
}
