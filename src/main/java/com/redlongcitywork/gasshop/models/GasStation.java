package com.redlongcitywork.gasshop.models;

/**
 *
 * @author redlongcity
 * 28/10/2017
 * model of gas station
 */
public class GasStation {
    
    private Integer id;
    
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GasStation{" + "id=" + id + ", name=" + name + '}';
    }
    
    
}
