package com.redlongcitywork.gasshop.jsonControllers;

import com.redlongcitywork.gasshop.models.Order;
import com.redlongcitywork.gasshop.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author redlongcity 29/10/2017
 */
@RestController
@RequestMapping("/json")
public class OrderController {

    @Autowired
    private OrderService service;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> list = service.findAll();
        if (list == null) {
            return new ResponseEntity<List<Order>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrder(@PathVariable("id") Integer id) {
        Order order = service.findById(id);
        if (order == null) {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity<Void> createOrder(@RequestBody Order order) {
        Order entity = service.findById(order.getId());
        if (entity != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Integer id,
            @RequestBody Order order) {
        Order entity = service.findById(id);
        if (entity == null) {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        entity.setDate(order.getDate());
        entity.setStatus(order.getStatus());
        entity.setGasPortionSet(order.getGasPortionSet());
        entity.setUser(order.getUser());
        service.updateOrder(entity);

        return new ResponseEntity<Order>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Integer id) {
        Order order = service.findById(id);
        if (order == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        service.deleteOrder(order);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
