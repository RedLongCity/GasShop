package com.redlongcitywork.gasshop.models;

/**
 *
 * @author redlongcity
 * 28/10/2017
 * model of user profile for controlling access
 */
public class UserProfile {
    
    
    private Integer id;
    
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserProfile{" + "id=" + id + ", type=" + type + '}';
    }
    
    
}
