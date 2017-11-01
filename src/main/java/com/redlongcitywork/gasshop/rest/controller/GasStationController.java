package com.redlongcitywork.gasshop.rest.controller;

import com.redlongcitywork.gasshop.models.GasStation;
import com.redlongcitywork.gasshop.service.GasStationService;
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
 * Controller for endpoints of GasStation
 */
@RestController
@RequestMapping("/json")
public class GasStationController {
    
    
    @Autowired
    private GasStationService service;

    @RequestMapping(value = "/station", method = RequestMethod.GET)
    public ResponseEntity<List<GasStation>> getGasStations() {
        List<GasStation> list = service.findAll();
        if (list == null) {
            return new ResponseEntity<List<GasStation>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<GasStation>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/station/{id}", method = RequestMethod.GET)
    public ResponseEntity<GasStation> getGasStation(@PathVariable("id") Integer id) {
        GasStation station = service.findById(id);
        if (station == null) {
            return new ResponseEntity<GasStation>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<GasStation>(station, HttpStatus.OK);
    }

    @RequestMapping(value = "/station", method = RequestMethod.POST)
    public ResponseEntity<Void> createGasStation(@RequestBody GasStation station) {
        GasStation entity = service.findById(station.getId());
        if (entity != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        service.saveGasStation(station);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/station/{id}", method = RequestMethod.PUT)
    public ResponseEntity<GasStation> updateGasStation(@PathVariable("id") Integer id,
            @RequestBody GasStation station) {
        GasStation entity = service.findById(id);
        if (entity == null) {
            return new ResponseEntity<GasStation>(HttpStatus.NOT_FOUND);
        }
        entity.setName(station.getName());
        service.updateGasStation(entity);

        return new ResponseEntity<GasStation>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/station/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGasStation(@PathVariable("id") Integer id) {
        GasStation station = service.findById(id);
        if (station == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        service.deleteGasStation(station);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
}
