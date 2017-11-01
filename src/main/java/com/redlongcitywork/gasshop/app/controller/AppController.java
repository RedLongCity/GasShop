package com.redlongcitywork.gasshop.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author redlongcity 28/10/2017 controller listens to user actions, gets data
 * and updates UI
 */
@Controller
@RequestMapping("/")
public class AppController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage() {
        return "admin";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    public String newUser() {
        return "registration";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String saveUser() {
        return "registration";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessSuccessfulPage() {
        return "accessDenied";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage() {
        return "accessSuccessful";
    }

}
