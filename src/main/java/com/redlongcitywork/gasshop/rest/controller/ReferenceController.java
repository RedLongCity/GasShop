package com.redlongcitywork.gasshop.rest.controller;

import com.redlongcitywork.gasshop.models.Fuel;
import com.redlongcitywork.gasshop.models.GasStation;
import com.redlongcitywork.gasshop.models.Reference;
import com.redlongcitywork.gasshop.service.FuelService;
import com.redlongcitywork.gasshop.service.GasStationService;
import com.redlongcitywork.gasshop.service.ReferenceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author redlongcity 29/10/2017
 */
@RestController
@RequestMapping("/json")
public class ReferenceController {

    @Autowired
    private ReferenceService service;

    @Autowired
    private GasStationService stationService;

    @Autowired
    private FuelService fuelService;

    @RequestMapping(value = "/reference", method = RequestMethod.GET)
    public ResponseEntity<List<Reference>> getReferences() {
        List<Reference> list = service.findAll();
        if (list == null) {
            return new ResponseEntity<List<Reference>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Reference>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/reference/{id}", method = RequestMethod.GET)
    public ResponseEntity<Reference> getReference(@PathVariable("id") Integer id) {
        Reference reference = service.findById(id);
        if (reference == null) {
            return new ResponseEntity<Reference>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Reference>(reference, HttpStatus.OK);
    }

    @RequestMapping(value = "/reference", method = RequestMethod.POST)
    public ResponseEntity<Void> createReference(@RequestBody Reference reference) {
        Reference entity = service.findById(reference.getId());
        if (entity != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        GasStation station = stationService.findById(entity.getStation().getId());
        entity.setStation(station);

        Fuel fuel = fuelService.findById(entity.getFuel().getId());
        entity.setFuel(fuel);

        service.saveReference(reference);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/reference/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Reference> updateReference(@PathVariable("id") Integer id,
            @RequestBody Reference reference) {
        Reference entity = service.findById(id);
        if (entity == null) {
            return new ResponseEntity<Reference>(HttpStatus.NOT_FOUND);
        }
        entity.setCost(reference.getCost());
        entity.setStation(reference.getStation());
        entity.setFuel(reference.getFuel());
        
        GasStation station = stationService.findById(entity.getStation().getId());
        entity.setStation(station);

        Fuel fuel = fuelService.findById(entity.getFuel().getId());
        entity.setFuel(fuel);

        service.updateReference(entity);
        return new ResponseEntity<Reference>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/reference/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReference(@PathVariable("id") Integer id) {
        Reference reference = service.findById(id);
        if (reference == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        service.deleteReference(reference);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value="/cost",method=RequestMethod.GET)
    public ResponseEntity<Float> getCost(
            @RequestParam(value="fuel") Integer fuelId,
            @RequestParam(value="station") Integer stationId){
        Fuel fuel = new Fuel();
        fuel.setId(fuelId);
        GasStation station = new GasStation();
        station.setId(stationId);
        Float cost = service.getCost(fuel, station);
        
        if(cost==null){
            return new ResponseEntity<Float>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Float>(cost,HttpStatus.OK);
    }
    
    @RequestMapping(value="/averagecost/{id}",method=RequestMethod.GET)
    public ResponseEntity<Float> getAverageCost(
            @PathVariable("id") Integer id){
        Fuel fuel = new Fuel();
        fuel.setId(id);
        Float averageCost = service.getAverageCost(fuel);
        if(averageCost==null){
            return new ResponseEntity<Float>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Float>(averageCost,HttpStatus.OK);
    }
    
    @RequestMapping(value="/referencebyfuel/{id}",method=RequestMethod.GET)
    public ResponseEntity<List<Reference>> getReferencesByFuel(
            @PathVariable("id") Integer id){
        Fuel fuel = new Fuel();
        fuel.setId(id);
        
        List<Reference> list = service.findByFuel(fuel);
        if(list==null){
            return new ResponseEntity<List<Reference>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Reference>>(list,HttpStatus.OK);
    }
}
