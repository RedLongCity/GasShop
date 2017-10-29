package com.redlongcitywork.gasshop.jsonControllers;

import com.redlongcitywork.gasshop.models.GasPortion;
import com.redlongcitywork.gasshop.service.GasPortionService;
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
public class GasPortionController {

    @Autowired
    private GasPortionService service;

    @RequestMapping(value = "/portion", method = RequestMethod.GET)
    public ResponseEntity<List<GasPortion>> getGasPortions() {
        List<GasPortion> list = service.findAll();
        if (list == null) {
            return new ResponseEntity<List<GasPortion>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GasPortion>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/portion/{id}", method = RequestMethod.GET)
    public ResponseEntity<GasPortion> getGasPortion(@PathVariable("id") Integer id) {
        GasPortion portion = service.findById(id);
        if (portion == null) {
            return new ResponseEntity<GasPortion>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<GasPortion>(portion, HttpStatus.OK);
    }

    @RequestMapping(value = "/portion", method = RequestMethod.POST)
    public ResponseEntity<Void> createGasPortion(@RequestBody GasPortion portion) {
        GasPortion entity = service.findById(portion.getId());
        if (entity != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/portion/{id}", method = RequestMethod.PUT)
    public ResponseEntity<GasPortion> updateGasPortion(@PathVariable("id") Integer id,
            @RequestBody GasPortion portion) {
        GasPortion entity = service.findById(id);
        if (entity == null) {
            return new ResponseEntity<GasPortion>(HttpStatus.NOT_FOUND);
        }
        entity.setAmount(portion.getAmount());
        entity.setStation(portion.getStation());
        entity.setFuel(portion.getFuel());
        service.updateGasPortion(entity);

        return new ResponseEntity<GasPortion>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/portion/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGasPortion(@PathVariable("id") Integer id) {
        GasPortion portion = service.findById(id);
        if (portion == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        service.deleteGasPortion(portion);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
