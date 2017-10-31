package com.redlongcitywork.gasshop.RestControllers;

import com.redlongcitywork.gasshop.models.Fuel;
import com.redlongcitywork.gasshop.service.FuelService;
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
 * @author redlongcity 
 * 29/10/2017 
 * Controller for endpoints of Fuel
 */
@RestController
@RequestMapping("/json")
public class FuelController {

    @Autowired
    private FuelService service;

    @RequestMapping(value = "/fuel", method = RequestMethod.GET)
    public ResponseEntity<List<Fuel>> getFuels() {
        List<Fuel> list = service.findAll();
        if (list == null) {
            return new ResponseEntity<List<Fuel>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Fuel>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/fuel/{id}", method = RequestMethod.GET)
    public ResponseEntity<Fuel> getFuel(@PathVariable("id") Integer id) {
        Fuel fuel = service.findById(id);
        if (fuel == null) {
            return new ResponseEntity<Fuel>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Fuel>(fuel, HttpStatus.OK);
    }

    @RequestMapping(value = "/fuel", method = RequestMethod.POST)
    public ResponseEntity<Void> createFuel(@RequestBody Fuel fuel) {
        Fuel entity = service.findById(fuel.getId());
        if (entity != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        service.saveFuel(fuel);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/fuel/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Fuel> updateFuel(@PathVariable("id") Integer id,
            @RequestBody Fuel fuel) {
        Fuel entity = service.findById(id);
        if (entity == null) {
            return new ResponseEntity<Fuel>(HttpStatus.NOT_FOUND);
        }
        entity.setName(fuel.getName());
        service.updateFuel(entity);

        return new ResponseEntity<Fuel>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/fuel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteFuel(@PathVariable("id") Integer id) {
        Fuel fuel = service.findById(id);
        if (fuel == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        service.deleteFuel(fuel);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
