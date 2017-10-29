package com.redlongcitywork.gasshop.jsonControllers;

import com.redlongcitywork.gasshop.models.User;
import com.redlongcitywork.gasshop.service.UserService;
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
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        List<User> list = service.findAll();
        if (list == null) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {
        User user = service.findById(id);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        User entity = service.findById(user.getId());
        if (entity != null) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id,
            @RequestBody User user) {
        User entity = service.findById(id);
        if (entity == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setTel(user.getTel());
        entity.setAddress(user.getAddress());
        entity.setPassword(user.getPassword());
        entity.setProfile(user.getProfile());
        service.updateUser(entity);

        return new ResponseEntity<User>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        User user = service.findById(id);
        if (user == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        service.deleteUser(user);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
