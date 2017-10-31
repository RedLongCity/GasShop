package com.redlongcitywork.gasshop.jsonControllers;

import com.redlongcitywork.gasshop.models.GasPortion;
import com.redlongcitywork.gasshop.models.Order;
import com.redlongcitywork.gasshop.models.User;
import com.redlongcitywork.gasshop.service.GasPortionService;
import com.redlongcitywork.gasshop.service.OrderService;
import com.redlongcitywork.gasshop.service.UserService;
import java.util.ArrayList;
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
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private GasPortionService portionService;

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
        List<GasPortion> list = new ArrayList<GasPortion>();
        if (entity != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        if(order.getGasPortionList()==null){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        for(GasPortion portion:order.getGasPortionList()){
            list.add(portionService.saveGasPortion(portion));
        }
        if(order.getUser()==null){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findById(order.getUser().getId());
        entity.setGasPortionList(list);
        entity.setUser(user);
        service.saveOrder(order);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Integer id,
            @RequestBody Order order) {
        Order entity = service.findById(id);
        List<GasPortion> list = new ArrayList();
        if (entity == null) {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        entity.setDate(order.getDate());
        entity.setStatus(order.getStatus());
        entity.setGasPortionList(order.getGasPortionList());
        entity.setUser(order.getUser());
        if(order.getGasPortionList()==null){
            return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
        }
        for(GasPortion portion: order.getGasPortionList()){
            GasPortion portionEntity = portionService.findById(portion.getId());
            if(portionEntity==null){
                list.add(portionService.saveGasPortion(portion));
            }else{
                list.add(portion);
            }
        }
        if(order.getUser()==null){
            return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findById(order.getUser().getId());
        entity.setGasPortionList(list);
        entity.setUser(user);
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
