package com.redlongcitywork.gasshop.jsonControllers;

import com.redlongcitywork.gasshop.models.Reference;
import com.redlongcitywork.gasshop.service.ReferenceService;
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
public class ReferenceController {

    @Autowired
    private ReferenceService service;

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
}
