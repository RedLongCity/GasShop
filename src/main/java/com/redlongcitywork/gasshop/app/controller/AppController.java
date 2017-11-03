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

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHomePage() {
        return "home";
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String getOrderPage() {
        return "order";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfile() {
        return "profile";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage() {
        return "admin";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    public String newUser() {
        return "registration";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessSuccessfulPage() {
        return "accessDenied";
    }

}
