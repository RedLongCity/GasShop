package com.redlongcitywork.gasshop.restcontroller;

import com.redlongcitywork.gasshop.models.UserProfile;
import com.redlongcitywork.gasshop.service.UserProfileService;
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
 * Controller for endpoints of UserProfile
 */
@RestController
@RequestMapping("/json")
public class UserProfileController {

    @Autowired
    private UserProfileService service;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ResponseEntity<List<UserProfile>> getUserProfiles() {
        List<UserProfile> list = service.findAll();
        if (list == null) {
            return new ResponseEntity<List<UserProfile>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserProfile>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable("id") Integer id) {
        UserProfile profile = service.findById(id);
        if (profile == null) {
            return new ResponseEntity<UserProfile>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserProfile>(profile, HttpStatus.OK);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity<Void> createUserProfile(@RequestBody UserProfile profile) {
        UserProfile entity = service.findById(profile.getId());
        if (entity != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        service.saveUserProfile(profile);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable("id") Integer id,
            @RequestBody UserProfile profile) {
        UserProfile entity = service.findById(id);
        if (entity == null) {
            return new ResponseEntity<UserProfile>(HttpStatus.NOT_FOUND);
        }
        entity.setType(profile.getType());
        service.updateUserProfile(entity);

        return new ResponseEntity<UserProfile>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUserProfile(@PathVariable("id") Integer id) {
        UserProfile profile = service.findById(id);
        if (profile == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        service.deleteUserProfile(profile);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
