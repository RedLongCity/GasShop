package com.redlongcitywork.gasshop.models;

/**
 *
 * @author redlongcity
 * 28/10/2017
 * model like unit of information
 */
public class Reference {
    
    private Integer id;
    
    private float cost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Reference{" + "id=" + id + ", cost=" + cost + '}';
    }
    
    
}
